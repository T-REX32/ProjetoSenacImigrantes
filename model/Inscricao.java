package model;

import java.time.LocalDate;

public class Inscricao {
    private Long id;
    private Imigrante imigrante;
    private Curso curso;
    private LocalDate dataInscricao;
    private String status;

    public Inscricao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Imigrante getImigrante() { return imigrante; }
    public void setImigrante(Imigrante imigrante) { this.imigrante = imigrante; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public LocalDate getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(LocalDate dataInscricao) { this.dataInscricao = dataInscricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
