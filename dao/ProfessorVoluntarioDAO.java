package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ProfessorVoluntarioDAO {
    public void inserir(Connection conexao, long idUsuario, String areaConhecimento,
            String experiencia) throws SQLException {
        String sql = "INSERT INTO " + PROFESSOR_VOLUNTARIO + " (" + PROFESSOR_VOLUNTARIO_USUARIO_ID
                + ", " + PROFESSOR_VOLUNTARIO_AREA_CONHECIMENTO + ", "
                + PROFESSOR_VOLUNTARIO_EXPERIENCIA + ") VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setLong(1, idUsuario);
            comando.setString(2, areaConhecimento);
            comando.setString(3, experiencia);
            comando.executeUpdate();
        }
    }
}
