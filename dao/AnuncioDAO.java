package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class AnuncioDAO {
    public long inserir(Connection conexao, long aulaId, String titulo,
            String descricao, LocalDate publicadoEm) throws SQLException {
        String sql = "INSERT INTO " + ANUNCIO + " (" + ANUNCIO_AULA_ID + ", "
                + ANUNCIO_TITULO + ", " + ANUNCIO_DESCRICAO + ", "
                + ANUNCIO_PUBLICADO_EM + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setLong(1, aulaId);
            comando.setString(2, titulo);
            comando.setString(3, descricao);
            if (publicadoEm == null) {
                comando.setDate(4, null);
            } else {
                comando.setDate(4, Date.valueOf(publicadoEm));
            }
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id do anúncio criado.");
                }
                return chaves.getLong(1);
            }
        }
    }
}
