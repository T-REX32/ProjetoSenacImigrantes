package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class TrilhaEnsinoDAO {
    public long inserir(Connection conexao, String titulo, String descricao) throws SQLException {
        String sql = "INSERT INTO " + TRILHA_ENSINO + " (" + TRILHA_ENSINO_TITULO + ", "
                + TRILHA_ENSINO_DESCRICAO + ") VALUES (?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, titulo);
            comando.setString(2, descricao);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da trilha criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
