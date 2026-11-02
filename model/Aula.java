package model;

public class Aula {
    private Long id;
    private Curso curso;
    private ProfessorVoluntario professorVoluntario;
    private String titulo;
    private String conteudo;
    private Integer duracaoMinutos;

    public Aula() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public ProfessorVoluntario getProfessorVoluntario() { return professorVoluntario; }
    public void setProfessorVoluntario(ProfessorVoluntario professorVoluntario) { this.professorVoluntario = professorVoluntario; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }
}
