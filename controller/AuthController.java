package controller;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import service.AuthService;
import util.Constantes;
import util.Json;
import util.Router;

public final class AuthController {
    private static final String[] PERFIS_AUTENTICADOS = {
        Constantes.PERFIL_IMIGRANTE,
        Constantes.PERFIL_VOLUNTARIO,
        Constantes.PERFIL_PROFESSOR_FORMADO,
        Constantes.PERFIL_REPRESENTANTE,
        Constantes.PERFIL_ADMIN
    };

    private AuthController() {
    }

    public static void registrar(Router router, AuthService service) {
        registrarCadastro(router, service, "/auth/cadastro/imigrante", Constantes.PERFIL_IMIGRANTE);
        registrarCadastro(router, service, "/auth/cadastro/voluntario", Constantes.PERFIL_VOLUNTARIO);
        registrarCadastro(router, service, "/auth/cadastro/professor-formado", Constantes.PERFIL_PROFESSOR_FORMADO);
        registrarCadastro(router, service, "/auth/cadastro/representante", Constantes.PERFIL_REPRESENTANTE);

        router.registrar("POST", "/auth/login", (requisicao, parametros) -> {
            Map<String, Object> entrada = lerCorpo(requisicao);
            return Router.Resposta.json(200, service.login(entrada));
        });
        router.registrar("POST", "/auth/logout", (requisicao, parametros) -> {
            service.logout(requisicao.getRequestHeaders().getFirst("Authorization"));
            return Router.Resposta.json(200, Map.of("mensagem", "Sessão encerrada."));
        }, PERFIS_AUTENTICADOS);
    }

    private static void registrarCadastro(Router router, AuthService service, String caminho, String tipoUsuario) {
        router.registrar("POST", caminho, (requisicao, parametros) -> {
            Map<String, Object> entrada = lerCorpo(requisicao);
            return Router.Resposta.json(201, service.cadastrar(tipoUsuario, entrada));
        });
    }

    private static Map<String, Object> lerCorpo(com.sun.net.httpserver.HttpExchange requisicao) throws Exception {
        String corpo = new String(requisicao.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        return Json.lerObjeto(corpo);
    }
}
