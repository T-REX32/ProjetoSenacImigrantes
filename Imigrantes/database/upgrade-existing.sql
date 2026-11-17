USE Imigrantes;

ALTER TABLE app_usuario
    ADD COLUMN telefone VARCHAR(40) NULL,
    ADD COLUMN senha_hash VARCHAR(255) NULL,
    ADD COLUMN senha_salt VARCHAR(64) NULL,
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    ADD COLUMN perfil VARCHAR(30) NOT NULL DEFAULT 'IMIGRANTE';

ALTER TABLE app_usuario
    ADD UNIQUE KEY uk_app_usuario_email (email);

ALTER TABLE app_aula
    ADD COLUMN trilha_id BIGINT NULL,
    ADD COLUMN professor_voluntario_id BIGINT NULL,
    ADD COLUMN disponivel BOOLEAN NOT NULL DEFAULT FALSE,
    ADD CONSTRAINT fk_app_aula_trilha FOREIGN KEY (trilha_id)
        REFERENCES app_trilha (id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_app_aula_professor FOREIGN KEY (professor_voluntario_id)
        REFERENCES app_professor_voluntario (usuario_id) ON DELETE SET NULL;
