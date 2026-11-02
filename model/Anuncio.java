package model;

import java.time.LocalDate;

public class Anuncio {
    private Long id;
    private Aula aula;
    private String titulo;
    private String descricao;
    private LocalDate publicadoEm;

    public Anuncio() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Aula getAula() { return aula; }
    public void setAula(Aula aula) { this.aula = aula; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getPublicadoEm() { return publicadoEm; }
    public void setPublicadoEm(LocalDate publicadoEm) { this.publicadoEm = publicadoEm; }
}
