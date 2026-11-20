package modelo.dao.formulario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import modelo.factory.conexao.ConexaoFactory;

public class FormularioInicialDAO {
    private final ConexaoFactory fabrica = new ConexaoFactory();

    public Map<String, Object> recuperar(long usuarioId) throws SQLException {
        String sql = "SELECT pais_origem, idioma_preferido, escolaridade, "
                + "experiencia_profissional, objetivo_profissional, formulario_preenchido "
                + "FROM app_imigrante WHERE usuario_id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Map<String, Object> dados = new LinkedHashMap<>();
                dados.put("pais_origem", rs.getString("pais_origem"));
                dados.put("idioma_preferido", rs.getString("idioma_preferido"));
                dados.put("escolaridade", rs.getString("escolaridade"));
                dados.put("experiencia_profissional", rs.getString("experiencia_profissional"));
                dados.put("objetivo_profissional", rs.getString("objetivo_profissional"));
                dados.put("formulario_preenchido", rs.getBoolean("formulario_preenchido"));
                return dados;
            }
        }
    }

    public void salvar(long usuarioId, String paisOrigem, String idioma, String escolaridade,
            String experiencia, String objetivo) throws SQLException {
        if (paisOrigem == null || paisOrigem.trim().isEmpty()
                || idioma == null || idioma.trim().isEmpty()
                || escolaridade == null || escolaridade.trim().isEmpty()
                || objetivo == null || objetivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha os campos obrigatórios do formulário.");
        }
        String sql = "UPDATE app_imigrante SET pais_origem = ?, idioma_preferido = ?, escolaridade = ?, "
                + "experiencia_profissional = ?, objetivo_profissional = ?, formulario_preenchido = TRUE, "
                + "formulario_preenchido_em = CURRENT_TIMESTAMP "
                + "WHERE usuario_id = ? AND formulario_preenchido = FALSE";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, paisOrigem.trim());
            ps.setString(2, idioma.trim());
            ps.setString(3, escolaridade.trim());
            ps.setString(4, experiencia == null ? null : experiencia.trim());
            ps.setString(5, objetivo.trim());
            ps.setLong(6, usuarioId);
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("O formulário já foi enviado ou não pertence a esta conta.");
            }
        }
    }
}
