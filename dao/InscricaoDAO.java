package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class InscricaoDAO {
    public long inserir(Connection conexao, long imigranteId, long cursoId,
            LocalDate dataInscricao, String status) throws SQLException {
        String sql = "INSERT INTO " + INSCRICAO + " (" + INSCRICAO_IMIGRANTE_ID + ", "
                + INSCRICAO_CURSO_ID + ", " + INSCRICAO_DATA_INSCRICAO + ", "
                + INSCRICAO_STATUS + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, imigranteId);
            comando.setLong(2, cursoId);
            comando.setDate(3, Date.valueOf(dataInscricao));
            comando.setString(4, status);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da inscrição criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
