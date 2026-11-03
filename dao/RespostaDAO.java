package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class RespostaDAO {
    public long inserir(Connection conexao, long imigranteId, long formularioId,
            String conteudo, LocalDate enviadaEm) throws SQLException {
        String sql = "INSERT INTO " + RESPOSTA + " (" + RESPOSTA_IMIGRANTE_ID + ", "
                + RESPOSTA_FORMULARIO_ID + ", " + RESPOSTA_CONTEUDO + ", "
                + RESPOSTA_ENVIADA_EM + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, imigranteId);
            comando.setLong(2, formularioId);
            comando.setString(3, conteudo);
            comando.setDate(4, Date.valueOf(enviadaEm));
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da resposta criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
