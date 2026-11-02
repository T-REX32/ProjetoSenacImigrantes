package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class Config {
    private final Properties propriedades;

    private Config(Properties propriedades) {
        this.propriedades = propriedades;
    }

    public static Config carregar() throws IOException {
        Path arquivo = Path.of("config.properties");
        if (!Files.isRegularFile(arquivo)) {
            throw new IllegalStateException(
                    "Arquivo config.properties não encontrado. Copie config.example.properties e configure os dados.");
        }

        Properties propriedades = new Properties();
        try (InputStream entrada = Files.newInputStream(arquivo)) {
            propriedades.load(entrada);
        }
        return new Config(propriedades);
    }

    public String get(String chave) {
        String valor = propriedades.getProperty(chave);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("Configuração obrigatória ausente: " + chave);
        }
        return valor.trim();
    }

    public int getInt(String chave) {
        try {
            return Integer.parseInt(get(chave));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Configuração inválida: " + chave, e);
        }
    }
}
