USE Imigrantes;

ALTER TABLE app_usuario
    ADD COLUMN cpf VARCHAR(14) NULL,
    ADD UNIQUE KEY uk_app_usuario_cpf (cpf);

ALTER TABLE app_imigrante
    ADD COLUMN escolaridade VARCHAR(120) NULL,
    ADD COLUMN experiencia_profissional TEXT NULL,
    ADD COLUMN objetivo_profissional TEXT NULL,
    ADD COLUMN formulario_preenchido BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN formulario_preenchido_em TIMESTAMP NULL;

ALTER TABLE app_representante
    ADD COLUMN tipo_organizacao VARCHAR(20) NOT NULL DEFAULT 'INSTITUICAO';

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
