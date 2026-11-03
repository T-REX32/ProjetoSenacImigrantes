package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class SalaDAO {
    public long inserir(Connection conexao, long representanteId, String nome,
            String localizacao) throws SQLException {
        String sql = "INSERT INTO " + SALA + " (" + SALA_REPRESENTANTE_ID + ", "
                + SALA_NOME + ", " + SALA_LOCALIZACAO + ") VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, representanteId);
            comando.setString(2, nome);
            comando.setString(3, localizacao);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id da sala criada.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
