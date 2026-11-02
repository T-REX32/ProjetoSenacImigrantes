package model;

import java.time.LocalDate;

public class Presenca {
    private Long id;
    private Aula aula;
    private Imigrante imigrante;
    private LocalDate registradaEm;
    private String status;

    public Presenca() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Aula getAula() { return aula; }
    public void setAula(Aula aula) { this.aula = aula; }
    public Imigrante getImigrante() { return imigrante; }
    public void setImigrante(Imigrante imigrante) { this.imigrante = imigrante; }
    public LocalDate getRegistradaEm() { return registradaEm; }
    public void setRegistradaEm(LocalDate registradaEm) { this.registradaEm = registradaEm; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
