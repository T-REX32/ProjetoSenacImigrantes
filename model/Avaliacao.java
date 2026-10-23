package model;

public class Avaliacao {
    private Long id;
    private Imigrante imigrante;
    private Aula aula;
    private ProfessorFormado professorFormado;
    private Integer nota;
    private String comentario;

    public Avaliacao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Imigrante getImigrante() { return imigrante; }
    public void setImigrante(Imigrante imigrante) { this.imigrante = imigrante; }
    public Aula getAula() { return aula; }
    public void setAula(Aula aula) { this.aula = aula; }
    public ProfessorFormado getProfessorFormado() { return professorFormado; }
    public void setProfessorFormado(ProfessorFormado professorFormado) { this.professorFormado = professorFormado; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
