package modelo.entidade.contato;
import java.util.ArrayList;
import java.util.List;

public class TrilhaEnsino {

    private Long id;
    private String titulo;
    private String descricao;

    private List<Aula> aulas = new ArrayList<>();

    public TrilhaEnsino() {}

    // Getters e Setters

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Métodos

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
    }

    public void publicar() {
        // Implementar lógica de publicação
    }

    public double calcularProgresso() {
        // Implementar cálculo do progresso
        return 0.0;
    }
}