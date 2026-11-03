package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ProgressoDAO {
    public long inserir(Connection conexao, long inscricaoId, int aulasConcluidas,
            int totalAulas, double percentual) throws SQLException {
        String sql = "INSERT INTO " + PROGRESSO + " (" + PROGRESSO_INSCRICAO_ID + ", "
                + PROGRESSO_AULAS_CONCLUIDAS + ", " + PROGRESSO_TOTAL_AULAS + ", "
                + PROGRESSO_PERCENTUAL + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, inscricaoId);
            comando.setInt(2, aulasConcluidas);
            comando.setInt(3, totalAulas);
            comando.setDouble(4, percentual);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id do progresso criado.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
