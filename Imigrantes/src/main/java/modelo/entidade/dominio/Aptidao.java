package modelo.entidade.dominio;

import modelo.entidade.usuario.NivelAptidao;

public class Aptidao {
    private Long id;
    private String nome;
    private NivelAptidao nivel = NivelAptidao.INICIANTE;
    private Long imigranteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public NivelAptidao getNivel() { return nivel; }
    public void setNivel(NivelAptidao nivel) { this.nivel = nivel; }
    public Long getImigranteId() { return imigranteId; }
    public void setImigranteId(Long imigranteId) { this.imigranteId = imigranteId; }
}
