package model;

import java.time.LocalDate;

public class Resposta {
    private Long id;
    private Imigrante imigrante;
    private Formulario formulario;
    private String conteudo;
    private LocalDate enviadaEm;

    public Resposta() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Imigrante getImigrante() { return imigrante; }
    public void setImigrante(Imigrante imigrante) { this.imigrante = imigrante; }
    public Formulario getFormulario() { return formulario; }
    public void setFormulario(Formulario formulario) { this.formulario = formulario; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDate getEnviadaEm() { return enviadaEm; }
    public void setEnviadaEm(LocalDate enviadaEm) { this.enviadaEm = enviadaEm; }
}
