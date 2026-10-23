package util;

import java.time.Instant;

public record Sessao(Long idUsuario, String tipoUsuario, Instant expiraEm) {
}
