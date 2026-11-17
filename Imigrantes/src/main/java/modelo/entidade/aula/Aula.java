package modelo.entidade.aula;

public class Aula {

    private Long id;
    private String titulo;
    private String conteudo;
    private Integer duracaoMinutos;
    private Long trilhaId;
    private Long professorVoluntarioId;
    private boolean disponivel;

    public Aula() {
    }

    public Aula(Long id, String titulo, String conteudo,
                Integer duracaoMinutos) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Long getTrilhaId() {
        return trilhaId;
    }

    public void setTrilhaId(Long trilhaId) {
        this.trilhaId = trilhaId;
    }

    public Long getProfessorVoluntarioId() {
        return professorVoluntarioId;
    }

    public void setProfessorVoluntarioId(Long professorVoluntarioId) {
        this.professorVoluntarioId = professorVoluntarioId;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void disponibilizar() {
        disponivel = true;
    }
}