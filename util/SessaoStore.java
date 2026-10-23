package util;

import com.sun.net.httpserver.HttpExchange;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public final class SessaoStore {
    private static final Duration VALIDADE = Duration.ofHours(2);
    private static final SecureRandom ALEATORIO = new SecureRandom();
    private final ConcurrentHashMap<String, Sessao> sessoes = new ConcurrentHashMap<>();

    public String criar(Long idUsuario, String tipoUsuario) {
        byte[] bytes = new byte[32];
        ALEATORIO.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        sessoes.put(token, new Sessao(idUsuario, tipoUsuario, Instant.now().plus(VALIDADE)));
        return token;
    }

    public Sessao resolver(HttpExchange requisicao) {
        String token = extrairToken(requisicao.getRequestHeaders().getFirst("Authorization"));
        if (token == null) {
            return null;
        }
        Sessao sessao = sessoes.get(token);
        if (sessao == null) {
            return null;
        }
        if (!sessao.expiraEm().isAfter(Instant.now())) {
            sessoes.remove(token, sessao);
            return null;
        }
        return sessao;
    }

    public void encerrar(String authorization) {
        String token = extrairToken(authorization);
        if (token != null) {
            sessoes.remove(token);
        }
    }

    private static String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        String token = authorization.substring(7).trim();
        return token.isEmpty() ? null : token;
    }
}
