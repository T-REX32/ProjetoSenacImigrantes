package model;

public class Representante {
    private Long id;
    private Usuario usuario;
    private Instituicao instituicao;
    private String cargo;

    public Representante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Instituicao getInstituicao() { return instituicao; }
    public void setInstituicao(Instituicao instituicao) { this.instituicao = instituicao; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
}
