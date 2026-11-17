package modelo.entidade.curso;

public class Curso {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer quantTurma;

    public Curso() {}

    public Curso(Long id) {
        this.id = id;
    }

    public Curso(String titulo, String descricao, Integer quantTurma) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.quantTurma = quantTurma;
    }

    public Curso(Long id, String titulo, String descricao, Integer quantTurma) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.quantTurma = quantTurma;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getQuantTurma() { return quantTurma; }
    public void setQuantTurma(Integer quantTurma) { this.quantTurma = quantTurma; }
}