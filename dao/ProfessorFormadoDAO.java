package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ProfessorFormadoDAO {
    public void inserir(Connection conexao, long idUsuario, String formacao,
            String especialidade) throws SQLException {
        String sql = "INSERT INTO " + PROFESSOR_FORMADO + " (" + PROFESSOR_FORMADO_USUARIO_ID
                + ", " + PROFESSOR_FORMADO_FORMACAO + ", " + PROFESSOR_FORMADO_ESPECIALIDADE
                + ") VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, idUsuario);
            comando.setString(2, formacao);
            comando.setString(3, especialidade);
            comando.executeUpdate();
        }
    }
}
