package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public final class AvaliacaoDAO {
    public long inserir(Connection conexao, long imigranteId, long aulaId,
            long professorFormadoId, Integer nota, String comentario) throws SQLException {
        String sql = "INSERT INTO " + AVALIACAO + " (" + AVALIACAO_IMIGRANTE_ID + ", "
                + AVALIACAO_AULA_ID + ", " + AVALIACAO_PROFESSOR_FORMADO_ID + ", "
                + AVALIACAO_NOTA + ", " + AVALIACAO_COMENTARIO + ") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, imigranteId);
            comando.setLong(2, aulaId);
            comando.setLong(3, professorFormadoId);
            if (nota == null) {
                comando.setNull(4, Types.INTEGER);
            } else {
                comando.setInt(4, nota);
            }
            comando.setString(5, comentario);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da avaliação criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
