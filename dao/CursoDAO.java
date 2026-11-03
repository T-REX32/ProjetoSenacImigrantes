package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class CursoDAO {
    public long inserir(Connection conexao, long trilhaId, String titulo, String descricao) throws SQLException {
        String sql = "INSERT INTO " + CURSO + " (" + CURSO_TRILHA_ID + ", " + CURSO_TITULO
                + ", " + CURSO_DESCRICAO + ") VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, trilhaId);
            comando.setString(2, titulo);
            comando.setString(3, descricao);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id do curso criado.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
