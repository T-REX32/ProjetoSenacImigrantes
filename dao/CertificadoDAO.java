package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class CertificadoDAO {
    public long inserir(Connection conexao, long inscricaoId, String codigoValidacao,
            LocalDate dataEmissao) throws SQLException {
        String sql = "INSERT INTO " + CERTIFICADO + " (" + CERTIFICADO_INSCRICAO_ID + ", "
                + CERTIFICADO_CODIGO_VALIDACAO + ", " + CERTIFICADO_DATA_EMISSAO + ") VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, inscricaoId);
            comando.setString(2, codigoValidacao);
            comando.setDate(3, Date.valueOf(dataEmissao));
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id do certificado criado.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
