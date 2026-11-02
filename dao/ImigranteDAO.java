package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ImigranteDAO {
    public void inserir(Connection conexao, long idUsuario, String paisOrigem,
            String idiomaPreferido, String statusMigratorio) throws SQLException {
        String sql = "INSERT INTO " + IMIGRANTE + " (" + IMIGRANTE_USUARIO_ID + ", "
                + IMIGRANTE_PAIS_ORIGEM + ", " + IMIGRANTE_IDIOMA_PREFERIDO + ", "
                + IMIGRANTE_STATUS_MIGRATORIO + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, idUsuario);
            comando.setString(2, paisOrigem);
            comando.setString(3, idiomaPreferido);
            comando.setString(4, statusMigratorio);
            comando.executeUpdate();
        }
    }
}
