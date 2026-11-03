package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class HorarioDAO {
    public long inserir(Connection conexao, long salaId, LocalDateTime inicio,
            LocalDateTime fim) throws SQLException {
        String sql = "INSERT INTO " + HORARIO + " (" + HORARIO_SALA_ID + ", "
                + HORARIO_INICIO + ", " + HORARIO_FIM + ") VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, salaId);
            comando.setTimestamp(2, Timestamp.valueOf(inicio));
            comando.setTimestamp(3, Timestamp.valueOf(fim));
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id do horário criado.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
