package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class FormularioDAO {
    public void inserir(Connection conexao, String titulo, String descricao) throws SQLException {
        String sql = "INSERT INTO " + FORMULARIO + " (" + FORMULARIO_TITULO + ", "
                + FORMULARIO_DESCRICAO + ") VALUES (?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, titulo);
            comando.setString(2, descricao);
            comando.executeUpdate();
        }
    }
}
