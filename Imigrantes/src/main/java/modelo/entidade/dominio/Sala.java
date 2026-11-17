package modelo.entidade.dominio;

public class Sala {
    private Long id;
    private String nome;
    private String endereco;
    private Integer capacidade;
    private Long representanteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public Long getRepresentanteId() { return representanteId; }
    public void setRepresentanteId(Long representanteId) { this.representanteId = representanteId; }
}
