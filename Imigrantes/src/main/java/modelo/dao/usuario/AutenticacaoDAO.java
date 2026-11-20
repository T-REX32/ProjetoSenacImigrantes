package modelo.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import modelo.dao.dominio.SenhaUtil;
import modelo.factory.conexao.ConexaoFactory;

public class AutenticacaoDAO {
    private final ConexaoFactory fabrica = new ConexaoFactory();

    public Map<String, Object> autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT u.id, u.nome, u.email, u.perfil, u.status, u.senha_hash, u.senha_salt, "
                + "COALESCE(i.formulario_preenchido, TRUE) AS formulario_preenchido "
                + "FROM app_usuario u LEFT JOIN app_imigrante i ON i.usuario_id = u.id WHERE u.email = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, email.trim().toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || rs.getString("senha_hash") == null
                        || !SenhaUtil.verificar(senha, rs.getString("senha_salt"), rs.getString("senha_hash"))) {
                    return null;
                }
                Map<String, Object> usuario = new LinkedHashMap<>();
                usuario.put("id", rs.getLong("id"));
                usuario.put("nome", rs.getString("nome"));
                usuario.put("email", rs.getString("email"));
                usuario.put("perfil", rs.getString("perfil"));
                usuario.put("status", rs.getString("status"));
                usuario.put("formularioPreenchido", rs.getBoolean("formulario_preenchido"));
                return usuario;
            }
        }
    }

    public Map<String, Object> obterEstadoConta(long usuarioId) throws SQLException {
        String sql = "SELECT u.status, u.perfil, COALESCE(i.formulario_preenchido, TRUE) "
                + "AS formulario_preenchido FROM app_usuario u "
                + "LEFT JOIN app_imigrante i ON i.usuario_id = u.id WHERE u.id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Map<String, Object> estado = new LinkedHashMap<>();
                estado.put("status", rs.getString("status"));
                estado.put("perfil", rs.getString("perfil"));
                estado.put("formularioPreenchido", rs.getBoolean("formulario_preenchido"));
                return estado;
            }
        }
    }
}
