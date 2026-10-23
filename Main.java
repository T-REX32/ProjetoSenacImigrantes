import com.sun.net.httpserver.HttpServer;
import controller.AuthController;
import controller.SaudeController;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.DriverManager;
import java.util.concurrent.Executors;
import service.AuthService;
import util.Config;
import util.Conexao;
import util.Router;
import util.SessaoStore;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try {
            Config config = Config.carregar();
            int porta = config.getInt("server.port");
            DriverManager.setLoginTimeout(2);
            Conexao conexao = new Conexao(config);
            SessaoStore sessoes = new SessaoStore();
            Router router = new Router(sessoes::resolver);
            SaudeController.registrar(router, conexao);
            AuthController.registrar(router, new AuthService(conexao, sessoes));

            HttpServer servidor = HttpServer.create(new InetSocketAddress(porta), 0);
            servidor.createContext("/api", router);
            servidor.setExecutor(Executors.newFixedThreadPool(32));
            servidor.start();
            System.out.println("API iniciada na porta " + porta + ".");
        } catch (IOException | RuntimeException e) {
            System.err.println("Não foi possível iniciar a API: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}
