package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class RepresentanteDAO {
    public void inserir(Connection conexao, long idUsuario, String instituicao) throws SQLException {
        String sql = "INSERT INTO " + REPRESENTANTE + " (" + REPRESENTANTE_USUARIO_ID + ", "
                + REPRESENTANTE_INSTITUICAO + ") VALUES (?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, idUsuario);
            comando.setString(2, instituicao);
            comando.executeUpdate();
        }
    }
}
