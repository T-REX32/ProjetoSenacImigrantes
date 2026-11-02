package dao;

import static util.MapeamentoBanco.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Constantes;

public final class UsuarioDAO {
    public boolean existeEmail(Connection conexao, String email) throws SQLException {
        String sql = "SELECT 1 FROM " + USUARIO + " WHERE " + USUARIO_EMAIL + " = ? LIMIT 1";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, email);
            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        }
    }

    public long inserir(Connection conexao, String nome, String email, String senhaHash,
            String telefone, String tipoUsuario) throws SQLException {
        String sql = "INSERT INTO " + USUARIO + " (" + USUARIO_NOME + ", " + USUARIO_EMAIL
                + ", " + USUARIO_SENHA + ", " + USUARIO_TELEFONE + ", " + USUARIO_STATUS
                + ", " + USUARIO_TIPO + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, nome);
            comando.setString(2, email);
            comando.setString(3, senhaHash);
            comando.setString(4, telefone);
            comando.setString(5, Constantes.STATUS_USUARIO_ATIVO);
            comando.setString(6, tipoUsuario);
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O banco não retornou o id do usuário criado.");
                }
                return chaves.getLong(1);
            }
        }
    }

    public Credenciais buscarCredenciaisPorEmail(Connection conexao, String email) throws SQLException {
        String sql = "SELECT " + USUARIO_ID + ", " + USUARIO_SENHA + ", " + USUARIO_STATUS
                + ", " + USUARIO_TIPO + " FROM " + USUARIO + " WHERE " + USUARIO_EMAIL + " = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, email);
            try (ResultSet resultado = comando.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }
                return new Credenciais(
                        resultado.getLong(USUARIO_ID),
                        resultado.getString(USUARIO_SENHA),
                        resultado.getString(USUARIO_STATUS),
                        resultado.getString(USUARIO_TIPO));
            }
        }
    }

    public record Credenciais(long idUsuario, String senhaHash, String status, String tipoUsuario) {
    }
}
