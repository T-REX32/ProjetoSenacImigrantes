CREATE DATABASE IF NOT EXISTS Imigrantes
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE Imigrantes;

CREATE TABLE IF NOT EXISTS app_usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(40),
    senha_hash VARCHAR(255),
    senha_salt VARCHAR(64),
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    perfil VARCHAR(30) NOT NULL DEFAULT 'IMIGRANTE',
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_usuario_email (email),
    UNIQUE KEY uk_app_usuario_cpf (cpf)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_imigrante (
    usuario_id BIGINT NOT NULL,
    pais_origem VARCHAR(100),
    idioma_preferido VARCHAR(100),
    status_migratorio VARCHAR(100),
    escolaridade VARCHAR(120),
    experiencia_profissional TEXT,
    objetivo_profissional TEXT,
    formulario_preenchido BOOLEAN NOT NULL DEFAULT FALSE,
    formulario_preenchido_em TIMESTAMP NULL,
    PRIMARY KEY (usuario_id),
    CONSTRAINT fk_app_imigrante_usuario FOREIGN KEY (usuario_id)
        REFERENCES app_usuario (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_professor_voluntario (
    usuario_id BIGINT NOT NULL,
    area_conhecimento VARCHAR(150),
    experiencia TEXT,
    PRIMARY KEY (usuario_id),
    CONSTRAINT fk_app_prof_vol_usuario FOREIGN KEY (usuario_id)
        REFERENCES app_usuario (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_professor_formado (
    usuario_id BIGINT NOT NULL,
    formacao VARCHAR(150),
    especialidade VARCHAR(150),
    PRIMARY KEY (usuario_id),
    CONSTRAINT fk_app_prof_formado_usuario FOREIGN KEY (usuario_id)
        REFERENCES app_usuario (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_representante (
    usuario_id BIGINT NOT NULL,
    instituicao VARCHAR(150),
    tipo_organizacao VARCHAR(20) NOT NULL DEFAULT 'INSTITUICAO',
    PRIMARY KEY (usuario_id),
    CONSTRAINT fk_app_representante_usuario FOREIGN KEY (usuario_id)
        REFERENCES app_usuario (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_auditoria_perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    autor_id BIGINT NOT NULL,
    campo_alterado VARCHAR(40) NOT NULL,
    alterado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_app_auditoria_autor_data (autor_id, alterado_em),
    CONSTRAINT fk_app_auditoria_autor FOREIGN KEY (autor_id)
        REFERENCES app_usuario (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descricao VARCHAR(255),
    quant_turma INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_trilha (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    carga_horaria_minutos INT NOT NULL DEFAULT 0,
    publicada BOOLEAN NOT NULL DEFAULT FALSE,
    criada_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_aula (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    conteudo TEXT,
    duracao_minutos INT NOT NULL,
    trilha_id BIGINT,
    professor_voluntario_id BIGINT,
    disponivel BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT fk_app_aula_trilha FOREIGN KEY (trilha_id)
        REFERENCES app_trilha (id) ON DELETE SET NULL,
    CONSTRAINT fk_app_aula_professor FOREIGN KEY (professor_voluntario_id)
        REFERENCES app_professor_voluntario (usuario_id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_formulario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_pergunta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    formulario_id BIGINT NOT NULL,
    texto VARCHAR(500) NOT NULL,
    tipo VARCHAR(30) NOT NULL DEFAULT 'TEXTO',
    PRIMARY KEY (id),
    CONSTRAINT fk_app_pergunta_formulario FOREIGN KEY (formulario_id)
        REFERENCES app_formulario (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_resposta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    conteudo TEXT NOT NULL,
    enviada_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    formulario_id BIGINT NOT NULL,
    pergunta_id BIGINT,
    imigrante_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_app_resposta_formulario FOREIGN KEY (formulario_id)
        REFERENCES app_formulario (id) ON DELETE CASCADE,
    CONSTRAINT fk_app_resposta_pergunta FOREIGN KEY (pergunta_id)
        REFERENCES app_pergunta (id) ON DELETE SET NULL,
    CONSTRAINT fk_app_resposta_imigrante FOREIGN KEY (imigrante_id)
        REFERENCES app_imigrante (usuario_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_aptidao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    nivel VARCHAR(20) NOT NULL DEFAULT 'INICIANTE',
    imigrante_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_app_aptidao_imigrante FOREIGN KEY (imigrante_id)
        REFERENCES app_imigrante (usuario_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_inscricao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_inscricao DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    imigrante_id BIGINT NOT NULL,
    trilha_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_inscricao_imigrante_trilha (imigrante_id, trilha_id),
    CONSTRAINT fk_app_inscricao_imigrante FOREIGN KEY (imigrante_id)
        REFERENCES app_imigrante (usuario_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_inscricao_trilha FOREIGN KEY (trilha_id)
        REFERENCES app_trilha (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_progresso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aulas_concluidas INT NOT NULL DEFAULT 0,
    total_aulas INT NOT NULL DEFAULT 0,
    percentual DECIMAL(5,2) NOT NULL DEFAULT 0,
    inscricao_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_progresso_inscricao (inscricao_id),
    CONSTRAINT fk_app_progresso_inscricao FOREIGN KEY (inscricao_id)
        REFERENCES app_inscricao (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_aula_concluida (
    id BIGINT NOT NULL AUTO_INCREMENT,
    progresso_id BIGINT NOT NULL,
    aula_id BIGINT NOT NULL,
    concluida_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_aula_concluida (progresso_id, aula_id),
    CONSTRAINT fk_app_aula_concluida_progresso FOREIGN KEY (progresso_id)
        REFERENCES app_progresso (id) ON DELETE CASCADE,
    CONSTRAINT fk_app_aula_concluida_aula FOREIGN KEY (aula_id)
        REFERENCES app_aula (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_certificado (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo_validacao CHAR(36) NOT NULL,
    data_emissao DATE NOT NULL,
    inscricao_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_certificado_codigo (codigo_validacao),
    UNIQUE KEY uk_app_certificado_inscricao (inscricao_id),
    CONSTRAINT fk_app_certificado_inscricao FOREIGN KEY (inscricao_id)
        REFERENCES app_inscricao (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_avaliacao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nota TINYINT NOT NULL,
    comentario TEXT,
    imigrante_id BIGINT NOT NULL,
    aula_id BIGINT NOT NULL,
    professor_formado_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_app_avaliacao_nota CHECK (nota BETWEEN 1 AND 5),
    CONSTRAINT fk_app_avaliacao_imigrante FOREIGN KEY (imigrante_id)
        REFERENCES app_imigrante (usuario_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_avaliacao_aula FOREIGN KEY (aula_id)
        REFERENCES app_aula (id) ON DELETE CASCADE,
    CONSTRAINT fk_app_avaliacao_professor FOREIGN KEY (professor_formado_id)
        REFERENCES app_professor_formado (usuario_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_sala (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL,
    representante_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_app_sala_representante FOREIGN KEY (representante_id)
        REFERENCES app_representante (usuario_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_horario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    sala_id BIGINT NOT NULL,
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_app_horario_sala FOREIGN KEY (sala_id)
        REFERENCES app_sala (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_presenca (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aula_id BIGINT NOT NULL,
    imigrante_id BIGINT NOT NULL,
    data DATE NOT NULL,
    presente BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_presenca_aula_imigrante_data (aula_id, imigrante_id, data),
    CONSTRAINT fk_app_presenca_aula FOREIGN KEY (aula_id)
        REFERENCES app_aula (id) ON DELETE CASCADE,
    CONSTRAINT fk_app_presenca_imigrante FOREIGN KEY (imigrante_id)
        REFERENCES app_imigrante (usuario_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS app_anuncio (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aula_id BIGINT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    publicado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_anuncio_aula (aula_id),
    CONSTRAINT fk_app_anuncio_aula FOREIGN KEY (aula_id)
        REFERENCES app_aula (id) ON DELETE CASCADE
) ENGINE=InnoDB;
