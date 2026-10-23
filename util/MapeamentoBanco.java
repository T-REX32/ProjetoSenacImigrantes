package util;

public final class MapeamentoBanco {
    public static final String USUARIO = "USUARIO";
    public static final String USUARIO_ID = "id";
    public static final String USUARIO_NOME = "nome";
    public static final String USUARIO_EMAIL = "email";
    public static final String USUARIO_SENHA = "senha";
    public static final String USUARIO_TELEFONE = "telefone";
    public static final String USUARIO_STATUS = "status";
    public static final String USUARIO_TIPO = "tipo_usuario";

    public static final String REPRESENTANTE = "REPRESENTANTE";
    public static final String REPRESENTANTE_USUARIO_ID = "usuario_id";
    public static final String REPRESENTANTE_INSTITUICAO = "instituicao";

    public static final String IMIGRANTE = "IMIGRANTE";
    public static final String IMIGRANTE_USUARIO_ID = "usuario_id";
    public static final String IMIGRANTE_PAIS_ORIGEM = "pais_origem";
    public static final String IMIGRANTE_IDIOMA_PREFERIDO = "idioma_preferido";
    public static final String IMIGRANTE_STATUS_MIGRATORIO = "status_migratorio";

    public static final String PROFESSOR_FORMADO = "PROFESSOR_FORMADO";
    public static final String PROFESSOR_FORMADO_USUARIO_ID = "usuario_id";
    public static final String PROFESSOR_FORMADO_FORMACAO = "formacao";
    public static final String PROFESSOR_FORMADO_ESPECIALIDADE = "especialidade";

    public static final String PROFESSOR_VOLUNTARIO = "PROFESSOR_VOLUNTARIO";
    public static final String PROFESSOR_VOLUNTARIO_USUARIO_ID = "usuario_id";
    public static final String PROFESSOR_VOLUNTARIO_AREA_CONHECIMENTO = "area_conhecimento";
    public static final String PROFESSOR_VOLUNTARIO_EXPERIENCIA = "experiencia";

    public static final String FORMULARIO = "FORMULARIO";
    public static final String FORMULARIO_ID = "id";
    public static final String FORMULARIO_TITULO = "titulo";
    public static final String FORMULARIO_DESCRICAO = "descricao";

    public static final String RESPOSTA = "RESPOSTA";
    public static final String RESPOSTA_ID = "id";
    public static final String RESPOSTA_IMIGRANTE_ID = "imigrante_id";
    public static final String RESPOSTA_FORMULARIO_ID = "formulario_id";
    public static final String RESPOSTA_CONTEUDO = "conteudo";
    public static final String RESPOSTA_ENVIADA_EM = "enviada_em";

    public static final String TRILHA_ENSINO = "TRILHA_ENSINO";
    public static final String TRILHA_ENSINO_ID = "id";
    public static final String TRILHA_ENSINO_TITULO = "titulo";
    public static final String TRILHA_ENSINO_DESCRICAO = "descricao";

    public static final String CURSO = "CURSO";
    public static final String CURSO_ID = "id";
    public static final String CURSO_TRILHA_ID = "trilha_id";
    public static final String CURSO_TITULO = "titulo";
    public static final String CURSO_DESCRICAO = "descricao";

    public static final String AULA = "AULA";
    public static final String AULA_ID = "id";
    public static final String AULA_CURSO_ID = "curso_id";
    public static final String AULA_PROFESSOR_VOLUNTARIO_ID = "professor_voluntario_id";
    public static final String AULA_TITULO = "titulo";
    public static final String AULA_CONTEUDO = "conteudo";
    public static final String AULA_DURACAO_MINUTOS = "duracao_minutos";

    public static final String ANUNCIO = "ANUNCIO";
    public static final String ANUNCIO_ID = "id";
    public static final String ANUNCIO_AULA_ID = "aula_id";
    public static final String ANUNCIO_TITULO = "titulo";
    public static final String ANUNCIO_DESCRICAO = "descricao";
    public static final String ANUNCIO_PUBLICADO_EM = "publicado_em";

    public static final String INSCRICAO = "INSCRICAO";
    public static final String INSCRICAO_ID = "id";
    public static final String INSCRICAO_IMIGRANTE_ID = "imigrante_id";
    public static final String INSCRICAO_CURSO_ID = "curso_id";
    public static final String INSCRICAO_DATA_INSCRICAO = "data_inscricao";
    public static final String INSCRICAO_STATUS = "status";

    public static final String PRESENCA = "PRESENCA";
    public static final String PRESENCA_ID = "id";
    public static final String PRESENCA_AULA_ID = "aula_id";
    public static final String PRESENCA_IMIGRANTE_ID = "imigrante_id";
    public static final String PRESENCA_REGISTRADA_EM = "registrada_em";
    public static final String PRESENCA_STATUS = "status";

    public static final String PROGRESSO = "PROGRESSO";
    public static final String PROGRESSO_ID = "id";
    public static final String PROGRESSO_INSCRICAO_ID = "inscricao_id";
    public static final String PROGRESSO_AULAS_CONCLUIDAS = "aulas_concluidas";
    public static final String PROGRESSO_TOTAL_AULAS = "total_aulas";
    public static final String PROGRESSO_PERCENTUAL = "percentual";

    public static final String CERTIFICADO = "CERTIFICADO";
    public static final String CERTIFICADO_ID = "id";
    public static final String CERTIFICADO_INSCRICAO_ID = "inscricao_id";
    public static final String CERTIFICADO_CODIGO_VALIDACAO = "codigo_validacao";
    public static final String CERTIFICADO_DATA_EMISSAO = "data_emissao";

    public static final String AVALIACAO = "AVALIACAO";
    public static final String AVALIACAO_ID = "id";
    public static final String AVALIACAO_IMIGRANTE_ID = "imigrante_id";
    public static final String AVALIACAO_AULA_ID = "aula_id";
    public static final String AVALIACAO_PROFESSOR_FORMADO_ID = "professor_formado_id";
    public static final String AVALIACAO_NOTA = "nota";
    public static final String AVALIACAO_COMENTARIO = "comentario";

    public static final String SALA = "SALA";
    public static final String SALA_ID = "id";
    public static final String SALA_REPRESENTANTE_ID = "representante_id";
    public static final String SALA_NOME = "nome";
    public static final String SALA_LOCALIZACAO = "localizacao";

    public static final String HORARIO = "HORARIO";
    public static final String HORARIO_ID = "id";
    public static final String HORARIO_SALA_ID = "sala_id";
    public static final String HORARIO_INICIO = "inicio";
    public static final String HORARIO_FIM = "fim";

    private MapeamentoBanco() {
    }
}
