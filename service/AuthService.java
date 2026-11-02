package service;

import dao.ImigranteDAO;
import dao.ProfessorFormadoDAO;
import dao.ProfessorVoluntarioDAO;
import dao.RepresentanteDAO;
import dao.UsuarioDAO;
import dao.UsuarioDAO.Credenciais;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import util.Conexao;
import util.Constantes;
import util.ErroApi;
import util.Senha;
import util.SessaoStore;

public final class AuthService {
    private final Conexao conexao;
    private final SessaoStore sessoes;
    private final Senha senha = new Senha();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ImigranteDAO imigranteDAO = new ImigranteDAO();
    private final ProfessorVoluntarioDAO voluntarioDAO = new ProfessorVoluntarioDAO();
    private final ProfessorFormadoDAO professorDAO = new ProfessorFormadoDAO();
    private final RepresentanteDAO representanteDAO = new RepresentanteDAO();

    public AuthService(Conexao conexao, SessaoStore sessoes) {
        this.conexao = conexao;
        this.sessoes = sessoes;
    }

    public Map<String, Object> cadastrar(String tipoUsuario, Map<String, Object> entrada) throws SQLException {
        String nome = obrigatorio(entrada, "nome");
        String email = obrigatorio(entrada, "email").toLowerCase(Locale.ROOT);
        String senhaTexto = obrigatorio(entrada, "senha");
        String telefone = opcional(entrada, "telefone");
        validarEmail(email);
        Map<String, Object> perfil = mapaOpcional(entrada.get("perfil"), "perfil");

        long idUsuario;
        try (Connection banco = conexao.abrir()) {
            banco.setAutoCommit(false);
            try {
                if (usuarioDAO.existeEmail(banco, email)) {
                    throw new ErroApi(409, "E-mail já cadastrado.");
                }
                idUsuario = usuarioDAO.inserir(
                        banco, nome, email, senha.gerarHash(senhaTexto), telefone, tipoUsuario);
                inserirPerfil(banco, idUsuario, tipoUsuario, perfil);
                banco.commit();
            } catch (SQLException | RuntimeException e) {
                try {
                    banco.rollback();
                } catch (SQLException falhaRollback) {
                    e.addSuppressed(falhaRollback);
                }
                if (e instanceof SQLException erroSql && ehViolacaoUnicidade(erroSql)) {
                    throw new ErroApi(409, "E-mail já cadastrado.");
                }
                throw e;
            }
        }
        return Map.of("idUsuario", idUsuario, "tipoUsuario", tipoUsuario);
    }

    public Map<String, Object> login(Map<String, Object> entrada) throws SQLException {
        String email = obrigatorio(entrada, "email").toLowerCase(Locale.ROOT);
        String senhaTexto = obrigatorio(entrada, "senha");
        validarEmail(email);

        Credenciais credenciais;
        try (Connection banco = conexao.abrir()) {
            credenciais = usuarioDAO.buscarCredenciaisPorEmail(banco, email);
        }
        if (credenciais == null || !senha.verificar(senhaTexto, credenciais.senhaHash())) {
            throw new ErroApi(401, "E-mail ou senha inválidos.");
        }
        if (!Constantes.STATUS_USUARIO_ATIVO.equals(credenciais.status())) {
            throw new ErroApi(403, "A conta está suspensa ou bloqueada.");
        }

        String token = sessoes.criar(credenciais.idUsuario(), credenciais.tipoUsuario());
        return Map.of(
                "token", token,
                "tipoUsuario", credenciais.tipoUsuario(),
                "idUsuario", credenciais.idUsuario());
    }

    public void logout(String authorization) {
        sessoes.encerrar(authorization);
    }

    private void inserirPerfil(Connection banco, long idUsuario, String tipoUsuario,
            Map<String, Object> perfil) throws SQLException {
        switch (tipoUsuario) {
            case Constantes.PERFIL_IMIGRANTE -> imigranteDAO.inserir(
                    banco, idUsuario,
                    opcional(perfil, "paisOrigem"),
                    opcional(perfil, "idiomaPreferido"),
                    opcional(perfil, "statusMigratorio"));
            case Constantes.PERFIL_VOLUNTARIO -> voluntarioDAO.inserir(
                    banco, idUsuario,
                    opcional(perfil, "areaConhecimento"),
                    opcional(perfil, "experiencia"));
            case Constantes.PERFIL_PROFESSOR_FORMADO -> professorDAO.inserir(
                    banco, idUsuario,
                    opcional(perfil, "formacao"),
                    opcional(perfil, "especialidade"));
            case Constantes.PERFIL_REPRESENTANTE -> representanteDAO.inserir(
                    banco, idUsuario, opcional(perfil, "instituicao"));
            default -> throw new IllegalArgumentException("Tipo de cadastro não permitido.");
        }
    }

    private static void validarEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

    private static String obrigatorio(Map<String, Object> dados, String campo) {
        String valor = opcional(dados, campo);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Campo obrigatório: " + campo + ".");
        }
        return valor.trim();
    }

    private static String opcional(Map<String, Object> dados, String campo) {
        Object valor = dados.get(campo);
        if (valor == null) {
            return null;
        }
        if (!(valor instanceof String texto)) {
            throw new IllegalArgumentException("O campo " + campo + " deve ser texto.");
        }
        return texto;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> mapaOpcional(Object valor, String campo) {
        if (valor == null) {
            return Map.of();
        }
        if (!(valor instanceof Map<?, ?> mapa)) {
            throw new IllegalArgumentException("O campo " + campo + " deve ser um objeto.");
        }
        return (Map<String, Object>) mapa;
    }

    private static boolean ehViolacaoUnicidade(SQLException erro) {
        for (SQLException atual = erro; atual != null; atual = atual.getNextException()) {
            if (atual.getErrorCode() == 1062) {
                return true;
            }
        }
        return false;
    }
}
