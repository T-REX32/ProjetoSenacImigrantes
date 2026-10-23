package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
    private final Config config;

    public Conexao(Config config) {
        this.config = config;
    }

    public Connection abrir() throws SQLException {
        return DriverManager.getConnection(
                config.get("db.url"),
                config.get("db.user"),
                config.get("db.password"));
    }
}
