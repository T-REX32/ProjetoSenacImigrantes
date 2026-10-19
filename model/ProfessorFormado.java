package model;

public class ProfessorFormado {
    private Long id;
    private Usuario usuario;
    private String formacao;
    private String registroProfissional;

    public ProfessorFormado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }
    public String getRegistroProfissional() { return registroProfissional; }
    public void setRegistroProfissional(String registroProfissional) { this.registroProfissional = registroProfissional; }
}
