package model;

public class Curso {
    private Long id;
    private TrilhaEnsino trilha;
    private String titulo;
    private String descricao;

    public Curso() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TrilhaEnsino getTrilha() { return trilha; }
    public void setTrilha(TrilhaEnsino trilha) { this.trilha = trilha; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
