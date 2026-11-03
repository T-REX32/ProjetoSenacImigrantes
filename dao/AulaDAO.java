package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public final class AulaDAO {
    public long inserir(Connection conexao, long cursoId, Long professorVoluntarioId,
            String titulo, String conteudo, Integer duracaoMinutos) throws SQLException {
        String sql = "INSERT INTO " + AULA + " (" + AULA_CURSO_ID + ", "
                + AULA_PROFESSOR_VOLUNTARIO_ID + ", " + AULA_TITULO + ", "
                + AULA_CONTEUDO + ", " + AULA_DURACAO_MINUTOS + ") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, cursoId);
            if (professorVoluntarioId == null) {
                comando.setNull(2, Types.INTEGER);
            } else {
                comando.setLong(2, professorVoluntarioId);
            }
            comando.setString(3, titulo);
            comando.setString(4, conteudo);
            if (duracaoMinutos == null) {
                comando.setNull(5, Types.INTEGER);
            } else {
                comando.setInt(5, duracaoMinutos);
            }
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da aula criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
