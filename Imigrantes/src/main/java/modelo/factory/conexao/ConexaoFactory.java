package modelo.factory.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    public Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não foi encontrado na aplicação!", e);
        }

        String url = variavelAmbiente("IMIGRANTES_DB_URL",
                "jdbc:mysql://localhost:3306/Imigrantes?useTimezone=true&serverTimezone=UTC");
        String usuario = variavelAmbiente("IMIGRANTES_DB_USER", "root");
        String senha = variavelAmbiente("IMIGRANTES_DB_PASSWORD", "root");
        return DriverManager.getConnection(url, usuario, senha);
    }

    private String variavelAmbiente(String nome, String padrao) {
        String valor = System.getenv(nome);
        return valor == null || valor.trim().isEmpty() ? padrao : valor.trim();
    }
}