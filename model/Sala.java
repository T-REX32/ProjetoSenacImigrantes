package model;

public class Sala {
    private Long id;
    private Representante representante;
    private String nome;
    private String localizacao;

    public Sala() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Representante getRepresentante() { return representante; }
    public void setRepresentante(Representante representante) { this.representante = representante; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}
