package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class PresencaDAO {
    public long inserir(Connection conexao, long aulaId, long imigranteId,
            LocalDate registradaEm, String status) throws SQLException {
        String sql = "INSERT INTO " + PRESENCA + " (" + PRESENCA_AULA_ID + ", "
                + PRESENCA_IMIGRANTE_ID + ", " + PRESENCA_REGISTRADA_EM + ", "
                + PRESENCA_STATUS + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, aulaId);
            comando.setLong(2, imigranteId);
            comando.setDate(3, Date.valueOf(registradaEm));
            comando.setString(4, status);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da presença criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
