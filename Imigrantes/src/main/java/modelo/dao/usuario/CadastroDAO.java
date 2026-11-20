package modelo.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.dao.dominio.SenhaUtil;
import modelo.factory.conexao.ConexaoFactory;

public class CadastroDAO {
    private final ConexaoFactory fabrica = new ConexaoFactory();

    public String cadastrarUsuario(String nome, String cpf, String email, String telefone, String senha,
            String perfil, String areaConhecimento, String experiencia, String formacao,
            String especialidade, String organizacao, String tipoOrganizacao) throws SQLException {
        validarCadastro(nome, cpf, email, telefone, senha, perfil);
        String salt = SenhaUtil.gerarSalt();
        try (Connection conexao = fabrica.getConexao()) {
            boolean autoCommit = conexao.getAutoCommit();
            conexao.setAutoCommit(false);
            String lockPerfil = null;
            try {
                lockPerfil = bloquearPrimeiroCadastro(conexao, perfil);
                boolean autoAprovado = lockPerfil != null && !perfilJaCadastrado(conexao, perfil);
                String status = "IMIGRANTE".equals(perfil) || autoAprovado ? "ATIVO" : "PENDENTE";
                String sql = "INSERT INTO app_usuario "
                        + "(nome, cpf, email, telefone, senha_hash, senha_salt, status, perfil) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                long usuarioId;
                try (PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, nome.trim());
                    ps.setString(2, cpf.replaceAll("[^0-9]", ""));
                    ps.setString(3, email.trim().toLowerCase());
                    ps.setString(4, telefone.trim());
                    ps.setString(5, SenhaUtil.gerarHash(senha, salt));
                    ps.setString(6, salt);
                    ps.setString(7, status);
                    ps.setString(8, perfil);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("O banco nao retornou o identificador do usuario.");
                        }
                        usuarioId = rs.getLong(1);
                    }
                }
                inserirPerfil(conexao, usuarioId, perfil, areaConhecimento, experiencia, formacao,
                        especialidade, organizacao, tipoOrganizacao);
                conexao.commit();
                return status;
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            } finally {
                try {
                    if (lockPerfil != null) {
                        liberarBloqueioPrimeiroCadastro(conexao, lockPerfil);
                    }
                } finally {
                    conexao.setAutoCommit(autoCommit);
                }
            }
        }
    }

    private String bloquearPrimeiroCadastro(Connection conexao, String perfil) throws SQLException {
        String habilitado = System.getenv("IMIGRANTES_AUTO_APPROVE_FIRST_PROFILES");
        if (!Boolean.parseBoolean(habilitado) || !perfilElegivelParaTeste(perfil)) {
            return null;
        }
        String nomeBloqueio = "imigrantes:first:" + perfil;
        try (PreparedStatement ps = conexao.prepareStatement("SELECT GET_LOCK(?, 10)")) {
            ps.setString(1, nomeBloqueio);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || rs.getInt(1) != 1) {
                    throw new SQLException("Nao foi possivel obter o bloqueio para aprovar o primeiro perfil.");
                }
            }
        }
        return nomeBloqueio;
    }

    private void liberarBloqueioPrimeiroCadastro(Connection conexao, String nomeBloqueio)
            throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement("SELECT RELEASE_LOCK(?)")) {
            ps.setString(1, nomeBloqueio);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || rs.getInt(1) != 1) {
                    throw new SQLException("Nao foi possivel liberar o bloqueio do primeiro perfil.");
                }
            }
        }
    }

    private boolean perfilJaCadastrado(Connection conexao, String perfil) throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement(
                "SELECT 1 FROM app_usuario WHERE perfil = ? LIMIT 1 FOR UPDATE")) {
            ps.setString(1, perfil);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean perfilElegivelParaTeste(String perfil) {
        return "PROFESSOR_FORMADO".equals(perfil) || "PROFESSOR_VOLUNTARIO".equals(perfil)
                || "REPRESENTANTE".equals(perfil);
    }

    private void validarCadastro(String nome, String cpf, String email, String telefone, String senha,
            String perfil) {
        if (nome == null || nome.trim().length() < 3 || nome.trim().length() > 150) {
            throw new IllegalArgumentException("Informe seu nome completo.");
        }
        if (!cpfValido(cpf)) {
            throw new IllegalArgumentException("Informe um CPF válido.");
        }
        if (email == null || !email.matches("(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$")) {
            throw new IllegalArgumentException("Informe um endereço de e-mail válido.");
        }
        if (telefone == null || telefone.replaceAll("[^0-9]", "").length() < 10) {
            throw new IllegalArgumentException("Informe um telefone com DDD.");
        }
        if (senha == null || senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!"IMIGRANTE".equals(perfil) && !"PROFESSOR_FORMADO".equals(perfil)
                && !"PROFESSOR_VOLUNTARIO".equals(perfil) && !"REPRESENTANTE".equals(perfil)) {
            throw new IllegalArgumentException("Selecione um tipo de cadastro válido.");
        }
    }

    private boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }
        String numeros = cpf.replaceAll("[^0-9]", "");
        if (numeros.length() != 11 || numeros.matches("(\\d)\\1{10}")) {
            return false;
        }
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (numeros.charAt(i) - '0') * (10 - i);
        }
        int digito = (soma * 10) % 11;
        if (digito == 10) {
            digito = 0;
        }
        if (digito != numeros.charAt(9) - '0') {
            return false;
        }
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (numeros.charAt(i) - '0') * (11 - i);
        }
        digito = (soma * 10) % 11;
        if (digito == 10) {
            digito = 0;
        }
        return digito == numeros.charAt(10) - '0';
    }

    private void inserirPerfil(Connection conexao, long usuarioId, String perfil, String areaConhecimento,
            String experiencia, String formacao, String especialidade, String organizacao,
            String tipoOrganizacao) throws SQLException {
        String sql;
        if ("IMIGRANTE".equals(perfil)) {
            sql = "INSERT INTO app_imigrante (usuario_id) VALUES (?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setLong(1, usuarioId);
                ps.executeUpdate();
            }
        } else if ("PROFESSOR_VOLUNTARIO".equals(perfil)) {
            if (areaConhecimento == null || areaConhecimento.trim().isEmpty()) {
                throw new IllegalArgumentException("Informe sua área de conhecimento.");
            }
            sql = "INSERT INTO app_professor_voluntario (usuario_id, area_conhecimento, experiencia) "
                    + "VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setLong(1, usuarioId);
                ps.setString(2, areaConhecimento.trim());
                ps.setString(3, experiencia == null ? null : experiencia.trim());
                ps.executeUpdate();
            }
        } else if ("PROFESSOR_FORMADO".equals(perfil)) {
            if (formacao == null || formacao.trim().isEmpty() || especialidade == null
                    || especialidade.trim().isEmpty()) {
                throw new IllegalArgumentException("Informe sua formação e especialidade.");
            }
            sql = "INSERT INTO app_professor_formado (usuario_id, formacao, especialidade) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setLong(1, usuarioId);
                ps.setString(2, formacao.trim());
                ps.setString(3, especialidade.trim());
                ps.executeUpdate();
            }
        } else {
            if (organizacao == null || organizacao.trim().isEmpty()
                    || (!"INSTITUICAO".equals(tipoOrganizacao) && !"ONG".equals(tipoOrganizacao))) {
                throw new IllegalArgumentException("Informe o nome e o tipo da instituição ou ONG.");
            }
            sql = "INSERT INTO app_representante (usuario_id, instituicao, tipo_organizacao) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setLong(1, usuarioId);
                ps.setString(2, organizacao.trim());
                ps.setString(3, tipoOrganizacao);
                ps.executeUpdate();
            }
        }
    }
}
