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

        return DriverManager.getConnection(
            "jdbc:mysql://localhost/Imigrantes?user=root&password=root&useTimezone=true&serverTimezone=UTC"
        );
    }
}