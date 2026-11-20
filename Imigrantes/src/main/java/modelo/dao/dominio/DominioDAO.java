package modelo.dao.dominio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import modelo.factory.conexao.ConexaoFactory;

public class DominioDAO {
    private static final List<ModuloDominio> MODULOS = criarModulos();
    private final ConexaoFactory fabrica = new ConexaoFactory();

    public List<ModuloDominio> listarModulos() {
        return MODULOS;
    }

    public ModuloDominio buscarModulo(String codigo) {
        for (ModuloDominio modulo : MODULOS) {
            if (modulo.getCodigo().equals(codigo)) {
                return modulo;
            }
        }
        return null;
    }

    public List<Map<String, Object>> listar(ModuloDominio modulo) throws SQLException {
        return listar(modulo, null, 0);
    }

    public List<Map<String, Object>> listar(ModuloDominio modulo, String perfil, long usuarioId)
            throws SQLException {
        List<Map<String, Object>> registros = new ArrayList<>();
        String filtro = "";
        if ("IMIGRANTE".equals(perfil) && "trilhas".equals(modulo.getCodigo())) {
            filtro = " WHERE publicada = TRUE";
        } else if ("IMIGRANTE".equals(perfil) && "aulas".equals(modulo.getCodigo())) {
            filtro = " WHERE disponivel = TRUE AND (trilha_id IS NULL OR trilha_id IN "
                    + "(SELECT id FROM app_trilha WHERE publicada = TRUE))";
        } else if ("IMIGRANTE".equals(perfil) && "anuncios".equals(modulo.getCodigo())) {
            filtro = " WHERE ativo = TRUE";
        } else if ("PROFESSOR_VOLUNTARIO".equals(perfil) && "aulas".equals(modulo.getCodigo())) {
            filtro = " WHERE professor_voluntario_id = ?";
        } else if ("REPRESENTANTE".equals(perfil) && "salas".equals(modulo.getCodigo())) {
            filtro = " WHERE representante_id = ?";
        } else if ("REPRESENTANTE".equals(perfil) && "horarios".equals(modulo.getCodigo())) {
            filtro = " WHERE sala_id IN (SELECT id FROM app_sala WHERE representante_id = ?)";
        }
        String sql = "SELECT " + colunasSql(modulo) + " FROM " + identificador(modulo.getTabela())
                + filtro + " ORDER BY " + identificador(modulo.getChave()) + " DESC";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (!filtro.isEmpty() && !"IMIGRANTE".equals(perfil)) {
                ps.setLong(1, usuarioId);
            }
            try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                registros.add(mapear(rs, modulo));
            }
            }
        }
        return registros;
    }

    public boolean representantePossuiSala(long representanteId, long salaId) throws SQLException {
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(
                     "SELECT 1 FROM app_sala WHERE id = ? AND representante_id = ?")) {
            ps.setLong(1, salaId);
            ps.setLong(2, representanteId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Map<String, Object> recuperarPerfil(long usuarioId) throws SQLException {
            String sql = "SELECT nome, email, cpf, telefone, perfil FROM app_usuario WHERE id = ?";
            try (Connection conexao = fabrica.getConexao();
                 PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setLong(1, usuarioId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    Map<String, Object> perfil = new LinkedHashMap<>();
                    perfil.put("nome", rs.getString("nome"));
                    perfil.put("email", rs.getString("email"));
                    perfil.put("cpf", rs.getString("cpf"));
                    perfil.put("telefone", rs.getString("telefone"));
                    perfil.put("perfil", rs.getString("perfil"));
                    return perfil;
                }
            }
    }

    public void atualizarPerfil(long usuarioId, String nome, String senhaAtual, String novaSenha,
                String confirmarSenha) throws SQLException {
            if (nome == null || nome.trim().length() < 3 || nome.trim().length() > 150) {
                throw new IllegalArgumentException("Informe seu nome completo.");
            }
            boolean alterarSenha = novaSenha != null && !novaSenha.isEmpty();
            if (alterarSenha && novaSenha.length() < 8) {
                throw new IllegalArgumentException("A nova senha deve ter pelo menos 8 caracteres.");
            }
            if (alterarSenha && !novaSenha.equals(confirmarSenha)) {
                throw new IllegalArgumentException("A confirmação da nova senha não confere.");
            }

            try (Connection conexao = fabrica.getConexao()) {
                boolean autoCommit = conexao.getAutoCommit();
                conexao.setAutoCommit(false);
                try {
                    String nomeAtual;
                    if (alterarSenha) {
                        try (PreparedStatement ps = conexao.prepareStatement(
                                "SELECT nome, senha_hash, senha_salt FROM app_usuario WHERE id = ? FOR UPDATE")) {
                            ps.setLong(1, usuarioId);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (!rs.next()) {
                                    throw new IllegalArgumentException("Conta não encontrada.");
                                }
                                nomeAtual = rs.getString("nome");
                                if (senhaAtual == null || !SenhaUtil.verificar(
                                        senhaAtual, rs.getString("senha_salt"), rs.getString("senha_hash"))) {
                                    throw new IllegalArgumentException("A senha atual está incorreta.");
                                }
                            }
                        }
                    } else {
                        try (PreparedStatement ps = conexao.prepareStatement(
                                "SELECT nome FROM app_usuario WHERE id = ? FOR UPDATE")) {
                            ps.setLong(1, usuarioId);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (!rs.next()) {
                                    throw new IllegalArgumentException("Conta não encontrada.");
                                }
                                nomeAtual = rs.getString("nome");
                            }
                        }
                    }

                    if (!nomeAtual.equals(nome.trim())) {
                        try (PreparedStatement ps = conexao.prepareStatement(
                                "UPDATE app_usuario SET nome = ? WHERE id = ?")) {
                            ps.setString(1, nome.trim());
                            ps.setLong(2, usuarioId);
                            ps.executeUpdate();
                        }
                        registrarAuditoria(conexao, usuarioId, "NOME");
                    }
                    if (alterarSenha) {
                        String salt = SenhaUtil.gerarSalt();
                        try (PreparedStatement ps = conexao.prepareStatement(
                                "UPDATE app_usuario SET senha_hash = ?, senha_salt = ? WHERE id = ?")) {
                            ps.setString(1, SenhaUtil.gerarHash(novaSenha, salt));
                            ps.setString(2, salt);
                            ps.setLong(3, usuarioId);
                            ps.executeUpdate();
                        }
                        registrarAuditoria(conexao, usuarioId, "SENHA");
                    }
                    conexao.commit();
                } catch (SQLException | RuntimeException e) {
                    conexao.rollback();
                    throw e;
                } finally {
                    conexao.setAutoCommit(autoCommit);
                }
            }
    }

    private void registrarAuditoria(Connection conexao, long usuarioId, String campo) throws SQLException {
            try (PreparedStatement ps = conexao.prepareStatement(
                    "INSERT INTO app_auditoria_perfil (autor_id, campo_alterado) VALUES (?, ?)")) {
                ps.setLong(1, usuarioId);
                ps.setString(2, campo);
                ps.executeUpdate();
            }
    }

    public Map<String, Object> recuperarFormularioInicial(long usuarioId) throws SQLException {
            String sql = "SELECT pais_origem, idioma_preferido, escolaridade, "
                    + "experiencia_profissional, objetivo_profissional, formulario_preenchido "
                    + "FROM app_imigrante WHERE usuario_id = ?";
            try (Connection conexao = fabrica.getConexao();
                 PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setLong(1, usuarioId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    Map<String, Object> dados = new LinkedHashMap<>();
                    dados.put("pais_origem", rs.getString("pais_origem"));
                    dados.put("idioma_preferido", rs.getString("idioma_preferido"));
                    dados.put("escolaridade", rs.getString("escolaridade"));
                    dados.put("experiencia_profissional", rs.getString("experiencia_profissional"));
                    dados.put("objetivo_profissional", rs.getString("objetivo_profissional"));
                    dados.put("formulario_preenchido", rs.getBoolean("formulario_preenchido"));
                    return dados;
                }
            }
    }

    public void salvarFormularioInicial(long usuarioId, String paisOrigem, String idioma,
                String escolaridade, String experiencia, String objetivo) throws SQLException {
            if (paisOrigem == null || paisOrigem.trim().isEmpty()
                    || idioma == null || idioma.trim().isEmpty()
                    || escolaridade == null || escolaridade.trim().isEmpty()
                    || objetivo == null || objetivo.trim().isEmpty()) {
                throw new IllegalArgumentException("Preencha os campos obrigatórios do formulário.");
            }
            String sql = "UPDATE app_imigrante SET pais_origem = ?, idioma_preferido = ?, escolaridade = ?, "
                    + "experiencia_profissional = ?, objetivo_profissional = ?, formulario_preenchido = TRUE, "
                    + "formulario_preenchido_em = CURRENT_TIMESTAMP "
                    + "WHERE usuario_id = ? AND formulario_preenchido = FALSE";
            try (Connection conexao = fabrica.getConexao();
                 PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, paisOrigem.trim());
                ps.setString(2, idioma.trim());
                ps.setString(3, escolaridade.trim());
                ps.setString(4, experiencia == null ? null : experiencia.trim());
                ps.setString(5, objetivo.trim());
                ps.setLong(6, usuarioId);
                if (ps.executeUpdate() == 0) {
                    throw new IllegalArgumentException("O formulário já foi enviado ou não pertence a esta conta.");
            }
        }
    }

    public Map<String, Object> recuperar(ModuloDominio modulo, long id) throws SQLException {
        String sql = "SELECT " + colunasSql(modulo) + " FROM " + identificador(modulo.getTabela())
                + " WHERE " + identificador(modulo.getChave()) + " = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs, modulo) : null;
            }
        }
    }

    public List<Map<String, Object>> listarOpcoes(CampoDominio campo) throws SQLException {
        return listarOpcoes(campo, null, 0);
    }

    public List<Map<String, Object>> listarOpcoes(CampoDominio campo, String perfil, long usuarioId)
            throws SQLException {
        List<Map<String, Object>> opcoes = new ArrayList<>();
        if (campo.getConsultaOpcoes() == null) {
            return opcoes;
        }
        String sql = campo.getConsultaOpcoes();
        boolean filtrarPorUsuario = false;
        if ("REPRESENTANTE".equals(perfil) && "sala_id".equals(campo.getNome())) {
            sql = "SELECT id, nome AS rotulo FROM app_sala WHERE representante_id = ? ORDER BY nome";
            filtrarPorUsuario = true;
        } else if ("PROFESSOR_VOLUNTARIO".equals(perfil)
                && "professor_voluntario_id".equals(campo.getNome())) {
            sql = "SELECT usuario_id AS id, area_conhecimento AS rotulo "
                    + "FROM app_professor_voluntario WHERE usuario_id = ?";
            filtrarPorUsuario = true;
        }
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (filtrarPorUsuario) {
                ps.setLong(1, usuarioId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> opcao = new LinkedHashMap<>();
                    opcao.put("id", rs.getObject("id"));
                    opcao.put("rotulo", rs.getString("rotulo"));
                    opcoes.add(opcao);
                }
            }
        }
        return opcoes;
    }

    public void salvar(ModuloDominio modulo, long id, Map<String, String> valores) throws SQLException {
        if (modulo.isSomenteLeitura()) {
            throw new IllegalArgumentException("Este modulo e gerado pelo sistema e nao pode ser alterado.");
        }
        if (modulo.getCampos().isEmpty()) {
            throw new IllegalArgumentException("Este modulo nao possui campos editaveis.");
        }
        validarNegocio(modulo, valores);
        try (Connection conexao = fabrica.getConexao()) {
            validarRelacionamentos(conexao, modulo, valores);
            boolean transacional = "inscricoes".equals(modulo.getCodigo());
            boolean autoCommit = conexao.getAutoCommit();
            if (transacional) {
                conexao.setAutoCommit(false);
            }
            try {
                if (id > 0) {
                    atualizar(conexao, modulo, id, valores);
                } else {
                    long novoId = inserir(conexao, modulo, valores);
                    if (transacional) {
                        criarProgressoInicial(conexao, novoId, valores.get("trilha_id"));
                    }
                }
                if (transacional) {
                    conexao.commit();
                }
            } catch (SQLException | RuntimeException e) {
                if (transacional) {
                    conexao.rollback();
                }
                throw e;
            } finally {
                if (transacional) {
                    conexao.setAutoCommit(autoCommit);
                }
            }
        }
    }

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

    public void excluir(ModuloDominio modulo, long id) throws SQLException {
        if (modulo.isSomenteLeitura()) {
            throw new IllegalArgumentException("Este modulo e gerado pelo sistema e nao pode ser excluido.");
        }
        String sql = "DELETE FROM " + identificador(modulo.getTabela()) + " WHERE "
                + identificador(modulo.getChave()) + " = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Registro nao encontrado.");
            }
        }
    }

    public void alterarStatusInscricao(long id, String status) throws SQLException {
        if ("ATIVA".equals(status)) {
            atualizarStatus(id, status, "PENDENTE");
        } else if ("CANCELADA".equals(status)) {
            atualizarStatus(id, status, "PENDENTE", "ATIVA");
        } else {
            throw new IllegalArgumentException("Status de inscricao invalido.");
        }
    }

    private void atualizarStatus(long id, String status, String... estadosAtuais) throws SQLException {
        String sql = "UPDATE app_inscricao SET status = ? WHERE id = ? AND status IN ("
                + marcadores(estadosAtuais.length) + ")";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, id);
            for (int i = 0; i < estadosAtuais.length; i++) {
                ps.setString(i + 3, estadosAtuais[i]);
            }
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("A inscricao nao permite essa mudanca de status.");
            }
        }
    }

    public void concluirAula(long inscricaoId, long aulaId) throws SQLException {
        try (Connection conexao = fabrica.getConexao()) {
            boolean autoCommit = conexao.getAutoCommit();
            conexao.setAutoCommit(false);
            try {
                long trilhaId;
                long progressoId;
                String inscricaoSql = "SELECT i.trilha_id, p.id FROM app_inscricao i "
                        + "JOIN app_progresso p ON p.inscricao_id = i.id "
                        + "WHERE i.id = ? AND i.status = 'ATIVA' FOR UPDATE";
                try (PreparedStatement ps = conexao.prepareStatement(inscricaoSql)) {
                    ps.setLong(1, inscricaoId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalArgumentException("A inscricao precisa estar ativa.");
                        }
                        trilhaId = rs.getLong(1);
                        progressoId = rs.getLong(2);
                    }
                }

                String aulaSql = "SELECT id FROM app_aula WHERE id = ? AND trilha_id = ? AND disponivel = TRUE";
                try (PreparedStatement ps = conexao.prepareStatement(aulaSql)) {
                    ps.setLong(1, aulaId);
                    ps.setLong(2, trilhaId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalArgumentException("A aula nao pertence a trilha ou nao esta disponivel.");
                        }
                    }
                }

                try (PreparedStatement ps = conexao.prepareStatement(
                        "INSERT INTO app_aula_concluida (progresso_id, aula_id) VALUES (?, ?)")) {
                    ps.setLong(1, progressoId);
                    ps.setLong(2, aulaId);
                    ps.executeUpdate();
                }

                int total = contarAulasDisponiveis(conexao, trilhaId);
                int concluidas = contarAulasConcluidas(conexao, progressoId, trilhaId);
                double percentual = total == 0 ? 0.0 : Math.min(100.0, concluidas * 100.0 / total);
                try (PreparedStatement ps = conexao.prepareStatement(
                        "UPDATE app_progresso SET aulas_concluidas = ?, total_aulas = ?, percentual = ? WHERE id = ?")) {
                    ps.setInt(1, concluidas);
                    ps.setInt(2, total);
                    ps.setDouble(3, percentual);
                    ps.setLong(4, progressoId);
                    ps.executeUpdate();
                }

                if (total > 0 && concluidas >= total) {
                    try (PreparedStatement ps = conexao.prepareStatement(
                            "UPDATE app_inscricao SET status = 'CONCLUIDA' WHERE id = ?")) {
                        ps.setLong(1, inscricaoId);
                        ps.executeUpdate();
                    }
                    emitirCertificado(conexao, inscricaoId);
                }
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            } finally {
                conexao.setAutoCommit(autoCommit);
            }
        }
    }

    public Map<String, Object> validarCertificado(String codigo) throws SQLException {
        String sql = "SELECT c.id, c.codigo_validacao, c.data_emissao, u.nome AS aluno, "
                + "t.titulo AS trilha FROM app_certificado c "
                + "JOIN app_inscricao i ON i.id = c.inscricao_id "
                + "JOIN app_imigrante im ON im.usuario_id = i.imigrante_id "
                + "JOIN app_usuario u ON u.id = im.usuario_id "
                + "JOIN app_trilha t ON t.id = i.trilha_id WHERE c.codigo_validacao = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Map<String, Object> certificado = new LinkedHashMap<>();
                certificado.put("id", rs.getLong("id"));
                certificado.put("codigo_validacao", rs.getString("codigo_validacao"));
                certificado.put("data_emissao", rs.getDate("data_emissao"));
                certificado.put("aluno", rs.getString("aluno"));
                certificado.put("trilha", rs.getString("trilha"));
                return certificado;
            }
        }
    }

    public Map<String, Object> autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT u.id, u.nome, u.email, u.perfil, u.status, u.senha_hash, u.senha_salt, "
                + "COALESCE(i.formulario_preenchido, TRUE) AS formulario_preenchido "
                + "FROM app_usuario u LEFT JOIN app_imigrante i ON i.usuario_id = u.id WHERE u.email = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, email.trim().toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || rs.getString("senha_hash") == null
                        || !SenhaUtil.verificar(senha, rs.getString("senha_salt"), rs.getString("senha_hash"))) {
                    return null;
                }
                Map<String, Object> usuario = new LinkedHashMap<>();
                usuario.put("id", rs.getLong("id"));
                usuario.put("nome", rs.getString("nome"));
                usuario.put("email", rs.getString("email"));
                usuario.put("perfil", rs.getString("perfil"));
                usuario.put("status", rs.getString("status"));
                usuario.put("formularioPreenchido", rs.getBoolean("formulario_preenchido"));
                return usuario;
            }
        }
    }

    public Map<String, Object> obterEstadoConta(long usuarioId) throws SQLException {
        String sql = "SELECT u.status, u.perfil, COALESCE(i.formulario_preenchido, TRUE) "
                + "AS formulario_preenchido FROM app_usuario u "
                + "LEFT JOIN app_imigrante i ON i.usuario_id = u.id WHERE u.id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Map<String, Object> estado = new LinkedHashMap<>();
                estado.put("status", rs.getString("status"));
                estado.put("perfil", rs.getString("perfil"));
                estado.put("formularioPreenchido", rs.getBoolean("formulario_preenchido"));
                return estado;
            }
        }
    }

    private long inserir(Connection conexao, ModuloDominio modulo, Map<String, String> valores)
            throws SQLException {
        List<String> colunas = new ArrayList<>();
        List<Object> parametros = new ArrayList<>();
        for (CampoDominio campo : modulo.getCampos()) {
            String valor = valores.get(campo.getNome());
            if ("senha".equals(campo.getNome())) {
                if (valor == null || valor.trim().isEmpty()) {
                    throw new IllegalArgumentException("Informe uma senha para o novo usuario.");
                }
                String salt = SenhaUtil.gerarSalt();
                colunas.add("senha_hash");
                parametros.add(SenhaUtil.gerarHash(valor, salt));
                colunas.add("senha_salt");
                parametros.add(salt);
            } else {
                colunas.add(campo.getNome());
                parametros.add(converter(campo, valor));
            }
        }
        String sql = "INSERT INTO " + identificador(modulo.getTabela()) + " ("
                + identificadores(colunas) + ") VALUES (" + marcadores(colunas.size()) + ")";
        try (PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            definirParametros(ps, parametros);
            ps.executeUpdate();
            if (!modulo.getChave().equals("id")) {
                return valoresLongo(valores.get(modulo.getChave()));
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new SQLException("O banco nao retornou o identificador criado.");
                }
                return rs.getLong(1);
            }
        }
    }

    private void atualizar(Connection conexao, ModuloDominio modulo, long id,
            Map<String, String> valores) throws SQLException {
        List<String> atribuicoes = new ArrayList<>();
        List<Object> parametros = new ArrayList<>();
        for (CampoDominio campo : modulo.getCampos()) {
            String valor = valores.get(campo.getNome());
            if ("senha".equals(campo.getNome())) {
                if (valor != null && !valor.trim().isEmpty()) {
                    String salt = SenhaUtil.gerarSalt();
                    atribuicoes.add("senha_hash = ?");
                    parametros.add(SenhaUtil.gerarHash(valor, salt));
                    atribuicoes.add("senha_salt = ?");
                    parametros.add(salt);
                }
            } else {
                atribuicoes.add(identificador(campo.getNome()) + " = ?");
                parametros.add(converter(campo, valor));
            }
        }
        if (atribuicoes.isEmpty()) {
            throw new IllegalArgumentException("Nenhum campo foi informado.");
        }
        parametros.add(id);
        String sql = "UPDATE " + identificador(modulo.getTabela()) + " SET "
                + join(atribuicoes, ", ") + " WHERE " + identificador(modulo.getChave()) + " = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            definirParametros(ps, parametros);
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Registro nao encontrado.");
            }
        }
    }

    private void criarProgressoInicial(Connection conexao, long inscricaoId, String trilhaId)
            throws SQLException {
        int total = contarAulasDisponiveis(conexao, valoresLongo(trilhaId));
        try (PreparedStatement ps = conexao.prepareStatement(
                "INSERT INTO app_progresso (inscricao_id, total_aulas) VALUES (?, ?)")) {
            ps.setLong(1, inscricaoId);
            ps.setInt(2, total);
            ps.executeUpdate();
        }
    }

    private int contarAulasDisponiveis(Connection conexao, long trilhaId) throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement(
                "SELECT COUNT(*) FROM app_aula WHERE trilha_id = ? AND disponivel = TRUE")) {
            ps.setLong(1, trilhaId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private int contarAulasConcluidas(Connection conexao, long progressoId, long trilhaId)
            throws SQLException {
        String sql = "SELECT COUNT(*) FROM app_aula_concluida c "
                + "JOIN app_aula a ON a.id = c.aula_id "
                + "WHERE c.progresso_id = ? AND a.trilha_id = ? AND a.disponivel = TRUE";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, progressoId);
            ps.setLong(2, trilhaId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private void emitirCertificado(Connection conexao, long inscricaoId) throws SQLException {
        String sql = "INSERT INTO app_certificado (codigo_validacao, data_emissao, inscricao_id) "
                + "VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setLong(3, inscricaoId);
            ps.executeUpdate();
        }
    }

    private void validarNegocio(ModuloDominio modulo, Map<String, String> valores) {
        if ("avaliacoes".equals(modulo.getCodigo())) {
            int nota = inteiroObrigatorio(valores.get("nota"), "nota");
            if (nota < 1 || nota > 5) {
                throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
            }
        }
        if ("aulas".equals(modulo.getCodigo())
                && inteiroObrigatorio(valores.get("duracao_minutos"), "duracao") <= 0) {
            throw new IllegalArgumentException("A duracao da aula deve ser maior que zero.");
        }
        if ("salas".equals(modulo.getCodigo())
                && inteiroObrigatorio(valores.get("capacidade"), "capacidade") <= 0) {
            throw new IllegalArgumentException("A capacidade da sala deve ser maior que zero.");
        }
        if ("horarios".equals(modulo.getCodigo())) {
            LocalTime inicio = horarioObrigatorio(valores.get("hora_inicio"));
            LocalTime fim = horarioObrigatorio(valores.get("hora_fim"));
            if (!fim.isAfter(inicio)) {
                throw new IllegalArgumentException("O horario final deve ser posterior ao horario inicial.");
            }
        }
    }

    private int inteiroObrigatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe um valor numerico para " + campo + ".");
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um valor numerico valido para " + campo + ".");
        }
    }

    private LocalTime horarioObrigatorio(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe horarios validos de inicio e termino.");
        }
        try {
            return LocalTime.parse(valor);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Informe horarios validos de inicio e termino.");
        }
    }

    private void validarRelacionamentos(Connection conexao, ModuloDominio modulo,
            Map<String, String> valores) throws SQLException {
        if (!"respostas".equals(modulo.getCodigo())) {
            return;
        }
        String perguntaId = valores.get("pergunta_id");
        if (perguntaId == null || perguntaId.trim().isEmpty()) {
            return;
        }
        String sql = "SELECT id FROM app_pergunta WHERE id = ? AND formulario_id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, numeroLongo(perguntaId, "pergunta"));
            ps.setLong(2, numeroLongo(valores.get("formulario_id"), "formulario"));
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("A pergunta selecionada nao pertence ao formulario.");
                }
            }
        }
    }

    private long numeroLongo(String valor, String campo) {
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Selecione um " + campo + " valido.");
        }
    }

    private Map<String, Object> mapear(ResultSet rs, ModuloDominio modulo) throws SQLException {
        Map<String, Object> registro = new LinkedHashMap<>();
        for (String coluna : modulo.getColunas()) {
            registro.put(coluna, rs.getObject(coluna));
        }
        return registro;
    }

    private Object converter(CampoDominio campo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            if ("checkbox".equals(campo.getTipo())) {
                return Boolean.FALSE;
            }
            if (campo.isObrigatorio()) {
                throw new IllegalArgumentException("O campo " + campo.getRotulo() + " e obrigatorio.");
            }
            return null;
        }
        try {
            if ("number".equals(campo.getTipo())) {
                return campo.getNome().endsWith("_id") || "usuario_id".equals(campo.getNome())
                        ? Long.valueOf(valor) : Integer.valueOf(valor);
            }
            if ("date".equals(campo.getTipo())) {
                return Date.valueOf(LocalDate.parse(valor));
            }
            if ("time".equals(campo.getTipo())) {
                return Time.valueOf(LocalTime.parse(valor));
            }
            if ("checkbox".equals(campo.getTipo())) {
                return Boolean.valueOf(valor);
            }
            if ("select".equals(campo.getTipo()) && !campo.getValores().isEmpty()
                    && !campo.getValores().contains(valor)) {
                throw new IllegalArgumentException("Valor invalido para " + campo.getRotulo() + ".");
            }
            return valor.trim();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor invalido para " + campo.getRotulo() + ".", e);
        }
    }

    private void definirParametros(PreparedStatement ps, List<Object> parametros) throws SQLException {
        for (int i = 0; i < parametros.size(); i++) {
            Object valor = parametros.get(i);
            if (valor == null) {
                ps.setNull(i + 1, Types.NULL);
            } else if (valor instanceof Boolean) {
                ps.setBoolean(i + 1, (Boolean) valor);
            } else if (valor instanceof Date) {
                ps.setDate(i + 1, (Date) valor);
            } else if (valor instanceof Time) {
                ps.setTime(i + 1, (Time) valor);
            } else if (valor instanceof Timestamp) {
                ps.setTimestamp(i + 1, (Timestamp) valor);
            } else if (valor instanceof Number) {
                ps.setObject(i + 1, valor);
            } else {
                ps.setString(i + 1, String.valueOf(valor));
            }
        }
    }

    private String colunasSql(ModuloDominio modulo) {
        List<String> colunas = new ArrayList<>();
        for (String coluna : modulo.getColunas()) {
            colunas.add(identificador(coluna));
        }
        return join(colunas, ", ");
    }

    private String identificadores(List<String> colunas) {
        List<String> seguras = new ArrayList<>();
        for (String coluna : colunas) {
            seguras.add(identificador(coluna));
        }
        return join(seguras, ", ");
    }

    private String identificador(String valor) {
        if (valor == null || !valor.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Identificador SQL invalido.");
        }
        return "`" + valor + "`";
    }

    private String marcadores(int quantidade) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < quantidade; i++) {
            if (i > 0) {
                resultado.append(", ");
            }
            resultado.append("?");
        }
        return resultado.toString();
    }

    private String join(List<String> itens, String separador) {
        StringBuilder resultado = new StringBuilder();
        for (String item : itens) {
            if (resultado.length() > 0) {
                resultado.append(separador);
            }
            resultado.append(item);
        }
        return resultado.toString();
    }

    private long valoresLongo(String valor) {
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Identificador numerico invalido.", e);
        }
    }

    private static CampoDominio campo(String nome, String rotulo, String tipo, boolean obrigatorio) {
        return new CampoDominio(nome, rotulo, tipo, obrigatorio, null, null);
    }

    private static CampoDominio opcoes(String nome, String rotulo, boolean obrigatorio, String... valores) {
        return new CampoDominio(nome, rotulo, "select", obrigatorio, null, Arrays.asList(valores));
    }

    private static CampoDominio relacao(String nome, String rotulo, boolean obrigatorio, String consulta) {
        return new CampoDominio(nome, rotulo, "select", obrigatorio, consulta, null);
    }

    private static ModuloDominio modulo(String codigo, String rotulo, String tabela, String chave,
            boolean somenteLeitura, List<String> colunas, CampoDominio... campos) {
        return new ModuloDominio(codigo, rotulo, tabela, chave, somenteLeitura,
                colunas, Arrays.asList(campos));
    }

    private static List<ModuloDominio> criarModulos() {
        List<ModuloDominio> modulos = new ArrayList<>();
        modulos.add(modulo("usuarios", "Usuarios", "app_usuario", "id", false,
                Arrays.asList("id", "nome", "email", "status", "perfil"),
                opcoes("status", "Status", true, "ATIVO", "INATIVO", "PENDENTE")));
        modulos.add(modulo("imigrantes", "Imigrantes", "app_imigrante", "usuario_id", false,
                Arrays.asList("usuario_id", "pais_origem", "idioma_preferido", "status_migratorio"),
                relacao("usuario_id", "Usuario", true,
                        "SELECT id, nome AS rotulo FROM app_usuario WHERE perfil = 'IMIGRANTE' ORDER BY nome"),
                campo("pais_origem", "Pais de origem", "text", false),
                campo("idioma_preferido", "Idioma preferido", "text", false),
                campo("status_migratorio", "Status migratorio", "text", false)));
        modulos.add(modulo("professores-voluntarios", "Professores voluntarios",
                "app_professor_voluntario", "usuario_id", false,
                Arrays.asList("usuario_id", "area_conhecimento", "experiencia"),
                relacao("usuario_id", "Usuario", true,
                        "SELECT id, nome AS rotulo FROM app_usuario WHERE perfil = 'PROFESSOR_VOLUNTARIO' ORDER BY nome"),
                campo("area_conhecimento", "Area de conhecimento", "text", true),
                campo("experiencia", "Experiencia", "textarea", false)));
        modulos.add(modulo("professores-formados", "Professores formados",
                "app_professor_formado", "usuario_id", false,
                Arrays.asList("usuario_id", "formacao", "especialidade"),
                relacao("usuario_id", "Usuario", true,
                        "SELECT id, nome AS rotulo FROM app_usuario WHERE perfil = 'PROFESSOR_FORMADO' ORDER BY nome"),
                campo("formacao", "Formacao", "text", true),
                campo("especialidade", "Especialidade", "text", true)));
        modulos.add(modulo("representantes", "Representantes", "app_representante", "usuario_id", false,
                Arrays.asList("usuario_id", "instituicao"),
                relacao("usuario_id", "Usuario", true,
                        "SELECT id, nome AS rotulo FROM app_usuario WHERE perfil = 'REPRESENTANTE' ORDER BY nome"),
                campo("instituicao", "Instituicao", "text", true)));
        modulos.add(modulo("formularios", "Formularios", "app_formulario", "id", false,
                Arrays.asList("id", "titulo", "descricao", "ativo"),
                campo("titulo", "Titulo", "text", true),
                campo("descricao", "Descricao", "textarea", false),
                campo("ativo", "Ativo", "checkbox", false)));
        modulos.add(modulo("perguntas", "Perguntas", "app_pergunta", "id", false,
                Arrays.asList("id", "formulario_id", "texto", "tipo"),
                relacao("formulario_id", "Formulario", true,
                        "SELECT id, titulo AS rotulo FROM app_formulario ORDER BY titulo"),
                campo("texto", "Pergunta", "text", true),
                opcoes("tipo", "Tipo de resposta", true, "TEXTO", "NUMERO", "DATA", "SIM_NAO")));
        modulos.add(modulo("respostas", "Respostas", "app_resposta", "id", false,
                Arrays.asList("id", "formulario_id", "pergunta_id", "imigrante_id", "conteudo", "enviada_em"),
                relacao("formulario_id", "Formulario", true,
                        "SELECT id, titulo AS rotulo FROM app_formulario ORDER BY titulo"),
                relacao("pergunta_id", "Pergunta", false,
                        "SELECT p.id, p.texto AS rotulo FROM app_pergunta p ORDER BY p.texto"),
                relacao("imigrante_id", "Imigrante", true,
                        "SELECT i.usuario_id AS id, u.nome AS rotulo FROM app_imigrante i "
                                + "JOIN app_usuario u ON u.id = i.usuario_id ORDER BY u.nome"),
                campo("conteudo", "Resposta", "textarea", true)));
        modulos.add(modulo("aptidoes", "Aptidoes", "app_aptidao", "id", false,
                Arrays.asList("id", "imigrante_id", "nome", "nivel"),
                relacao("imigrante_id", "Imigrante", true,
                        "SELECT i.usuario_id AS id, u.nome AS rotulo FROM app_imigrante i "
                                + "JOIN app_usuario u ON u.id = i.usuario_id ORDER BY u.nome"),
                campo("nome", "Aptidao", "text", true),
                opcoes("nivel", "Nivel", true, "INICIANTE", "INTERMEDIARIO", "AVANCADO")));
        modulos.add(modulo("trilhas", "Trilhas de ensino", "app_trilha", "id", false,
                Arrays.asList("id", "titulo", "descricao", "carga_horaria_minutos", "publicada"),
                campo("titulo", "Titulo", "text", true),
                campo("descricao", "Descricao", "textarea", false),
                campo("carga_horaria_minutos", "Carga horaria (minutos)", "number", true),
                campo("publicada", "Publicada", "checkbox", false)));
        modulos.add(modulo("aulas", "Aulas", "app_aula", "id", false,
                Arrays.asList("id", "titulo", "conteudo", "duracao_minutos", "trilha_id",
                        "professor_voluntario_id", "disponivel"),
                campo("titulo", "Titulo", "text", true),
                campo("conteudo", "Conteudo", "textarea", false),
                campo("duracao_minutos", "Duracao (minutos)", "number", true),
                relacao("trilha_id", "Trilha", false,
                        "SELECT id, titulo AS rotulo FROM app_trilha ORDER BY titulo"),
                relacao("professor_voluntario_id", "Professor voluntario", false,
                        "SELECT p.usuario_id AS id, u.nome AS rotulo FROM app_professor_voluntario p "
                                + "JOIN app_usuario u ON u.id = p.usuario_id ORDER BY u.nome"),
                campo("disponivel", "Disponibilizada", "checkbox", false)));
        modulos.add(modulo("inscricoes", "Inscricoes", "app_inscricao", "id", false,
                Arrays.asList("id", "data_inscricao", "status", "imigrante_id", "trilha_id"),
                campo("data_inscricao", "Data da inscricao", "date", true),
                relacao("imigrante_id", "Imigrante", true,
                        "SELECT i.usuario_id AS id, u.nome AS rotulo FROM app_imigrante i "
                                + "JOIN app_usuario u ON u.id = i.usuario_id ORDER BY u.nome"),
                relacao("trilha_id", "Trilha", true,
                        "SELECT id, titulo AS rotulo FROM app_trilha ORDER BY titulo")));
        modulos.add(modulo("avaliacoes", "Avaliacoes", "app_avaliacao", "id", false,
                Arrays.asList("id", "nota", "comentario", "imigrante_id", "aula_id", "professor_formado_id"),
                campo("nota", "Nota (1 a 5)", "number", true),
                campo("comentario", "Comentario", "textarea", false),
                relacao("imigrante_id", "Imigrante", true,
                        "SELECT i.usuario_id AS id, u.nome AS rotulo FROM app_imigrante i "
                                + "JOIN app_usuario u ON u.id = i.usuario_id ORDER BY u.nome"),
                relacao("aula_id", "Aula", true, "SELECT id, titulo AS rotulo FROM app_aula ORDER BY titulo"),
                relacao("professor_formado_id", "Professor formado", true,
                        "SELECT p.usuario_id AS id, u.nome AS rotulo FROM app_professor_formado p "
                                + "JOIN app_usuario u ON u.id = p.usuario_id ORDER BY u.nome")));
        modulos.add(modulo("progressos", "Progressos", "app_progresso", "id", true,
                Arrays.asList("id", "inscricao_id", "aulas_concluidas", "total_aulas", "percentual")));
        modulos.add(modulo("certificados", "Certificados", "app_certificado", "id", true,
                Arrays.asList("id", "inscricao_id", "codigo_validacao", "data_emissao")));
        modulos.add(modulo("salas", "Salas", "app_sala", "id", false,
                Arrays.asList("id", "nome", "endereco", "capacidade", "representante_id"),
                campo("nome", "Nome", "text", true),
                campo("endereco", "Endereco", "text", true),
                campo("capacidade", "Capacidade", "number", true),
                relacao("representante_id", "Representante", true,
                        "SELECT r.usuario_id AS id, u.nome AS rotulo FROM app_representante r "
                                + "JOIN app_usuario u ON u.id = r.usuario_id ORDER BY u.nome")));
        modulos.add(modulo("horarios", "Horarios", "app_horario", "id", false,
                Arrays.asList("id", "sala_id", "data", "hora_inicio", "hora_fim"),
                relacao("sala_id", "Sala", true, "SELECT id, nome AS rotulo FROM app_sala ORDER BY nome"),
                campo("data", "Data", "date", true),
                campo("hora_inicio", "Hora de inicio", "time", true),
                campo("hora_fim", "Hora de termino", "time", true)));
        modulos.add(modulo("presencas", "Presencas", "app_presenca", "id", false,
                Arrays.asList("id", "aula_id", "imigrante_id", "data", "presente"),
                relacao("aula_id", "Aula", true, "SELECT id, titulo AS rotulo FROM app_aula ORDER BY titulo"),
                relacao("imigrante_id", "Imigrante", true,
                        "SELECT i.usuario_id AS id, u.nome AS rotulo FROM app_imigrante i "
                                + "JOIN app_usuario u ON u.id = i.usuario_id ORDER BY u.nome"),
                campo("data", "Data", "date", true),
                campo("presente", "Presente", "checkbox", false)));
        modulos.add(modulo("anuncios", "Anuncios", "app_anuncio", "id", false,
                Arrays.asList("id", "aula_id", "titulo", "descricao", "publicado_em", "ativo"),
                relacao("aula_id", "Aula", true, "SELECT id, titulo AS rotulo FROM app_aula ORDER BY titulo"),
                campo("titulo", "Titulo", "text", true),
                campo("descricao", "Descricao", "textarea", false),
                campo("ativo", "Ativo", "checkbox", false)));
        return modulos;
    }
}
