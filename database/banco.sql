-- =====================================================================
-- Banco de dados da plataforma de apoio a imigrantes
-- Gerado a partir do modelo banco.mwb (MySQL Workbench)
-- 18 tabelas | 21 chaves estrangeiras | InnoDB
-- =====================================================================

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mydb`;

CREATE TABLE IF NOT EXISTS `USUARIO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(150) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `telefone` VARCHAR(20) NULL DEFAULT NULL,
  `status` VARCHAR(20) NOT NULL,
  `tipo_usuario` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_usuario_email` (`email`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `REPRESENTANTE` (
  `usuario_id` INT NOT NULL,
  `instituicao` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `fk_representante_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `IMIGRANTE` (
  `usuario_id` INT NOT NULL,
  `pais_origem` VARCHAR(80) NULL DEFAULT NULL,
  `idioma_preferido` VARCHAR(50) NULL DEFAULT NULL,
  `status_migratorio` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `fk_imigrante_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `PROFESSOR_FORMADO` (
  `usuario_id` INT NOT NULL,
  `formacao` VARCHAR(150) NULL DEFAULT NULL,
  `especialidade` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `fk_prof_formado_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `PROFESSOR_VOLUNTARIO` (
  `usuario_id` INT NOT NULL,
  `area_conhecimento` VARCHAR(150) NULL DEFAULT NULL,
  `experiencia` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `fk_prof_voluntario_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `USUARIO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `FORMULARIO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(150) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `RESPOSTA` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `imigrante_id` INT NOT NULL,
  `formulario_id` INT NOT NULL,
  `conteudo` TEXT NULL DEFAULT NULL,
  `enviada_em` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_resposta_imigrante` (`imigrante_id`),
  INDEX `idx_resposta_formulario` (`formulario_id`),
  CONSTRAINT `fk_resposta_imigrante` FOREIGN KEY (`imigrante_id`) REFERENCES `IMIGRANTE` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_resposta_formulario` FOREIGN KEY (`formulario_id`) REFERENCES `FORMULARIO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `TRILHA_ENSINO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(150) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `CURSO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `trilha_id` INT NOT NULL,
  `titulo` VARCHAR(150) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_curso_trilha` (`trilha_id`),
  CONSTRAINT `fk_curso_trilha` FOREIGN KEY (`trilha_id`) REFERENCES `TRILHA_ENSINO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `AULA` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `curso_id` INT NOT NULL,
  `professor_voluntario_id` INT NULL DEFAULT NULL,
  `titulo` VARCHAR(150) NOT NULL,
  `conteudo` VARCHAR(255) NULL DEFAULT NULL,
  `duracao_minutos` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_aula_curso` (`curso_id`),
  INDEX `idx_aula_prof_voluntario` (`professor_voluntario_id`),
  CONSTRAINT `fk_aula_curso` FOREIGN KEY (`curso_id`) REFERENCES `CURSO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_aula_prof_voluntario` FOREIGN KEY (`professor_voluntario_id`) REFERENCES `PROFESSOR_VOLUNTARIO` (`usuario_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `ANUNCIO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `aula_id` INT NOT NULL,
  `titulo` VARCHAR(150) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `publicado_em` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_anuncio_aula` (`aula_id`),
  CONSTRAINT `fk_anuncio_aula` FOREIGN KEY (`aula_id`) REFERENCES `AULA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `INSCRICAO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `imigrante_id` INT NOT NULL,
  `curso_id` INT NOT NULL,
  `data_inscricao` DATE NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_inscricao_imigrante` (`imigrante_id`),
  INDEX `idx_inscricao_curso` (`curso_id`),
  CONSTRAINT `fk_inscricao_imigrante` FOREIGN KEY (`imigrante_id`) REFERENCES `IMIGRANTE` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_inscricao_curso` FOREIGN KEY (`curso_id`) REFERENCES `CURSO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `PRESENCA` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `aula_id` INT NOT NULL,
  `imigrante_id` INT NOT NULL,
  `registrada_em` DATE NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_presenca_aula` (`aula_id`),
  INDEX `idx_presenca_imigrante` (`imigrante_id`),
  CONSTRAINT `fk_presenca_aula` FOREIGN KEY (`aula_id`) REFERENCES `AULA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_presenca_imigrante` FOREIGN KEY (`imigrante_id`) REFERENCES `IMIGRANTE` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `PROGRESSO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `inscricao_id` INT NOT NULL,
  `aulas_concluidas` INT NOT NULL DEFAULT 0,
  `total_aulas` INT NOT NULL DEFAULT 0,
  `percentual` DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_progresso_inscricao` (`inscricao_id`),
  CONSTRAINT `fk_progresso_inscricao` FOREIGN KEY (`inscricao_id`) REFERENCES `INSCRICAO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `CERTIFICADO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `inscricao_id` INT NOT NULL,
  `codigo_validacao` VARCHAR(64) NOT NULL,
  `data_emissao` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_certificado_inscricao` (`inscricao_id`),
  UNIQUE INDEX `uk_certificado_codigo` (`codigo_validacao`),
  CONSTRAINT `fk_certificado_inscricao` FOREIGN KEY (`inscricao_id`) REFERENCES `INSCRICAO` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `AVALIACAO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `imigrante_id` INT NOT NULL,
  `aula_id` INT NOT NULL,
  `professor_formado_id` INT NOT NULL,
  `nota` INT NULL DEFAULT NULL,
  `comentario` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_avaliacao_imigrante` (`imigrante_id`),
  INDEX `idx_avaliacao_aula` (`aula_id`),
  INDEX `idx_avaliacao_prof_formado` (`professor_formado_id`),
  CONSTRAINT `fk_avaliacao_imigrante` FOREIGN KEY (`imigrante_id`) REFERENCES `IMIGRANTE` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_avaliacao_aula` FOREIGN KEY (`aula_id`) REFERENCES `AULA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_avaliacao_prof_formado` FOREIGN KEY (`professor_formado_id`) REFERENCES `PROFESSOR_FORMADO` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `SALA` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `representante_id` INT NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `localizacao` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_sala_representante` (`representante_id`),
  CONSTRAINT `fk_sala_representante` FOREIGN KEY (`representante_id`) REFERENCES `REPRESENTANTE` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `HORARIO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sala_id` INT NOT NULL,
  `inicio` DATETIME NOT NULL,
  `fim` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_horario_sala` (`sala_id`),
  CONSTRAINT `fk_horario_sala` FOREIGN KEY (`sala_id`) REFERENCES `SALA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;
