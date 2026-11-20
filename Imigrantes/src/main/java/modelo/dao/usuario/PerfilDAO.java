package modelo.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import modelo.dao.dominio.SenhaUtil;
import modelo.factory.conexao.ConexaoFactory;

public class PerfilDAO {
    private final ConexaoFactory fabrica = new ConexaoFactory();

    public Map<String, Object> recuperarPerfil(long usuarioId) throws SQLException {
        String sql = "SELECT nome, email, cpf, telefone, perfil FROM app_usuario WHERE id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Map<String, Object> perfil = new LinkedHashMap<>();
                perfil.put("nome", rs.getString("nome"));
                perfil.put("email", rs.getString("email"));
                perfil.put("cpf", rs.getString("cpf"));
                perfil.put("telefone", rs.getString("telefone"));
                perfil.put("perfil", rs.getString("perfil"));
                return perfil;
            }
        }
    }

    public void atualizarPerfil(long usuarioId, String nome, String senhaAtual, String novaSenha,
            String confirmarSenha) throws SQLException {
        if (nome == null || nome.trim().length() < 3 || nome.trim().length() > 150) {
            throw new IllegalArgumentException("Informe seu nome completo.");
        }
        boolean alterarSenha = novaSenha != null && !novaSenha.isEmpty();
        if (alterarSenha && novaSenha.length() < 8) {
            throw new IllegalArgumentException("A nova senha deve ter pelo menos 8 caracteres.");
        }
        if (alterarSenha && !novaSenha.equals(confirmarSenha)) {
            throw new IllegalArgumentException("A confirmação da nova senha não confere.");
        }

        try (Connection conexao = fabrica.getConexao()) {
            boolean autoCommit = conexao.getAutoCommit();
            conexao.setAutoCommit(false);
            try {
                String nomeAtual;
                if (alterarSenha) {
                    try (PreparedStatement ps = conexao.prepareStatement(
                            "SELECT nome, senha_hash, senha_salt FROM app_usuario WHERE id = ? FOR UPDATE")) {
                        ps.setLong(1, usuarioId);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) {
                                throw new IllegalArgumentException("Conta não encontrada.");
                            }
                            nomeAtual = rs.getString("nome");
                            if (senhaAtual == null || !SenhaUtil.verificar(
                                    senhaAtual, rs.getString("senha_salt"), rs.getString("senha_hash"))) {
                                throw new IllegalArgumentException("A senha atual está incorreta.");
                            }
                        }
                    }
                } else {
                    try (PreparedStatement ps = conexao.prepareStatement(
                            "SELECT nome FROM app_usuario WHERE id = ? FOR UPDATE")) {
                        ps.setLong(1, usuarioId);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) {
                                throw new IllegalArgumentException("Conta não encontrada.");
                            }
                            nomeAtual = rs.getString("nome");
                        }
                    }
                }

                if (!nomeAtual.equals(nome.trim())) {
                    try (PreparedStatement ps = conexao.prepareStatement(
                            "UPDATE app_usuario SET nome = ? WHERE id = ?")) {
                        ps.setString(1, nome.trim());
                        ps.setLong(2, usuarioId);
                        ps.executeUpdate();
                    }
                    registrarAuditoria(conexao, usuarioId, "NOME");
                }
                if (alterarSenha) {
                    String salt = SenhaUtil.gerarSalt();
                    try (PreparedStatement ps = conexao.prepareStatement(
                            "UPDATE app_usuario SET senha_hash = ?, senha_salt = ? WHERE id = ?")) {
                        ps.setString(1, SenhaUtil.gerarHash(novaSenha, salt));
                        ps.setString(2, salt);
                        ps.setLong(3, usuarioId);
                        ps.executeUpdate();
                    }
                    registrarAuditoria(conexao, usuarioId, "SENHA");
                }
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            } finally {
                conexao.setAutoCommit(autoCommit);
            }
        }
    }

    private void registrarAuditoria(Connection conexao, long usuarioId, String campo) throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement(
                "INSERT INTO app_auditoria_perfil (autor_id, campo_alterado) VALUES (?, ?)")) {
            ps.setLong(1, usuarioId);
            ps.setString(2, campo);
            ps.executeUpdate();
        }
    }
}
