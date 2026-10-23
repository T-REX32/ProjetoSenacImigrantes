package util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Router implements HttpHandler {
    private static final int LIMITE_CORPO_BYTES = 1024 * 1024;

    @FunctionalInterface
    public interface Tratador {
        Resposta tratar(HttpExchange requisicao, Map<String, String> parametros) throws Exception;
    }

    @FunctionalInterface
    public interface ResolverPerfil {
        Sessao obterSessao(HttpExchange requisicao);
    }

    public record Resposta(int status, Object corpo) {
        public static Resposta json(int status, Object corpo) {
            return new Resposta(status, corpo);
        }
    }

    private record Rota(String metodo, String caminho, String[] segmentos, Set<String> perfis, Tratador tratador) {
    }

    private final List<Rota> rotas = new ArrayList<>();
    private final ResolverPerfil resolverPerfil;

    public Router(ResolverPerfil resolverPerfil) {
        this.resolverPerfil = resolverPerfil;
    }

    public void registrar(String metodo, String caminho, Tratador tratador, String... perfis) {
        rotas.add(new Rota(
                metodo.toUpperCase(),
                normalizar(caminho),
                segmentos(normalizar(caminho)),
                Set.of(perfis),
                tratador));
    }

    @Override
    public void handle(HttpExchange requisicao) throws IOException {
        adicionarCors(requisicao);
        if ("OPTIONS".equalsIgnoreCase(requisicao.getRequestMethod())) {
            requisicao.getResponseHeaders().set("Access-Control-Max-Age", "3600");
            requisicao.sendResponseHeaders(204, -1);
            requisicao.close();
            return;
        }

        try {
            byte[] corpo = requisicao.getRequestBody().readNBytes(LIMITE_CORPO_BYTES + 1);
            if (corpo.length > LIMITE_CORPO_BYTES) {
                enviar(requisicao, 413, Map.of("erro", "O corpo da requisição excede o limite de 1 MB."));
                return;
            }
            requisicao.setStreams(new ByteArrayInputStream(corpo), null);

            String caminhoRecebido = requisicao.getRequestURI().getPath();
            String caminho = normalizar(caminhoRecebido.equals("/api")
                    ? "/"
                    : caminhoRecebido.startsWith("/api/") ? caminhoRecebido.substring(4) : caminhoRecebido);
            Rota encontrada = null;
            Map<String, String> parametros = null;
            for (Rota rota : rotas) {
                Map<String, String> valores = combinar(rota, caminho);
                if (valores != null) {
                    if (rota.metodo().equalsIgnoreCase(requisicao.getRequestMethod())) {
                        encontrada = rota;
                        parametros = valores;
                        break;
                    }
                    if (encontrada == null) {
                        encontrada = rota;
                    }
                }
            }
            if (encontrada == null) {
                enviar(requisicao, 404, Map.of("erro", "Rota não encontrada."));
                return;
            }
            if (!encontrada.metodo().equalsIgnoreCase(requisicao.getRequestMethod())) {
                enviar(requisicao, 405, Map.of("erro", "Método não permitido."));
                return;
            }
            if (!encontrada.perfis().isEmpty()) {
                Sessao sessao = resolverPerfil == null ? null : resolverPerfil.obterSessao(requisicao);
                if (sessao == null) {
                    throw new ErroApi(401, "Autenticação necessária.");
                }
                requisicao.setAttribute("sessao", sessao);
                if (!encontrada.perfis().contains(sessao.tipoUsuario())) {
                    throw new ErroApi(403, "Acesso não permitido para este perfil.");
                }
            }

            Resposta resposta = encontrada.tratador().tratar(requisicao, parametros);
            enviar(requisicao, resposta.status(), resposta.corpo());
        } catch (ErroApi e) {
            enviar(requisicao, e.getStatus(), Map.of("erro", e.getMessage()));
        } catch (IllegalArgumentException e) {
            enviar(requisicao, 400, Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Erro ao processar requisição: " + e.getMessage());
            e.printStackTrace(System.err);
            enviar(requisicao, 500, Map.of("erro", "Erro interno do servidor."));
        } finally {
            requisicao.close();
        }
    }

    private static void adicionarCors(HttpExchange requisicao) {
        requisicao.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        requisicao.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        requisicao.getResponseHeaders().set("Access-Control-Allow-Headers", "Authorization, Content-Type");
    }

    private static void enviar(HttpExchange requisicao, int status, Object corpo) throws IOException {
        byte[] bytes = Json.gerar(corpo).getBytes(StandardCharsets.UTF_8);
        requisicao.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        requisicao.sendResponseHeaders(status, bytes.length);
        requisicao.getResponseBody().write(bytes);
    }

    private static Map<String, String> combinar(Rota rota, String caminho) {
        String[] partes = segmentos(caminho);
        if (partes.length != rota.segmentos().length) {
            return null;
        }
        Map<String, String> parametros = new HashMap<>();
        for (int i = 0; i < partes.length; i++) {
            String padrao = rota.segmentos()[i];
            if (padrao.startsWith("{") && padrao.endsWith("}")) {
                parametros.put(padrao.substring(1, padrao.length() - 1), decodificar(partes[i]));
            } else if (!padrao.equals(partes[i])) {
                return null;
            }
        }
        return parametros;
    }

    private static String decodificar(String valor) {
        return URLDecoder.decode(valor, StandardCharsets.UTF_8);
    }

    private static String normalizar(String caminho) {
        if (caminho == null || caminho.isBlank()) {
            return "/";
        }
        String resultado = caminho.startsWith("/") ? caminho : "/" + caminho;
        return resultado.length() > 1 && resultado.endsWith("/")
                ? resultado.substring(0, resultado.length() - 1)
                : resultado;
    }

    private static String[] segmentos(String caminho) {
        return "/".equals(caminho) ? new String[0] : caminho.substring(1).split("/");
    }
}
