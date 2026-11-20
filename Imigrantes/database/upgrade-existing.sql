USE Imigrantes;

ALTER TABLE app_usuario
    ADD COLUMN cpf VARCHAR(14) NULL,
    ADD COLUMN telefone VARCHAR(40) NULL,
    ADD COLUMN senha_hash VARCHAR(255) NULL,
    ADD COLUMN senha_salt VARCHAR(64) NULL,
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    ADD COLUMN perfil VARCHAR(30) NOT NULL DEFAULT 'IMIGRANTE';

ALTER TABLE app_usuario
    ADD UNIQUE KEY uk_app_usuario_cpf (cpf),
    ADD UNIQUE KEY uk_app_usuario_email (email);

ALTER TABLE app_imigrante
    ADD COLUMN escolaridade VARCHAR(120) NULL,
    ADD COLUMN experiencia_profissional TEXT NULL,
    ADD COLUMN objetivo_profissional TEXT NULL,
    ADD COLUMN formulario_preenchido BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN formulario_preenchido_em TIMESTAMP NULL;

ALTER TABLE app_representante
    ADD COLUMN tipo_organizacao VARCHAR(20) NOT NULL DEFAULT 'INSTITUICAO';

ALTER TABLE app_aula
    ADD COLUMN trilha_id BIGINT NULL,
    ADD COLUMN professor_voluntario_id BIGINT NULL,
    ADD COLUMN disponivel BOOLEAN NOT NULL DEFAULT FALSE,
    ADD CONSTRAINT fk_app_aula_trilha FOREIGN KEY (trilha_id)
        REFERENCES app_trilha (id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_app_aula_professor FOREIGN KEY (professor_voluntario_id)
        REFERENCES app_professor_voluntario (usuario_id) ON DELETE SET NULL;

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
