package controller;

import java.sql.Connection;
import util.Conexao;
import util.ErroApi;
import util.Router;

public final class SaudeController {
    private SaudeController() {
    }

    public static void registrar(Router router, Conexao conexao) {
        router.registrar("GET", "/saude", (requisicao, parametros) -> {
            try (Connection conexaoBanco = conexao.abrir()) {
                if (!conexaoBanco.isValid(1)) {
                    throw new ErroApi(500, "Não foi possível conectar ao banco de dados.");
                }
            } catch (java.sql.SQLException e) {
                System.err.println("Falha ao testar conexão com o banco: " + e.getMessage());
                throw new ErroApi(500, "Não foi possível conectar ao banco de dados.");
            }
            return Router.Resposta.json(200, java.util.Map.of("status", "ok"));
        });
    }
}
