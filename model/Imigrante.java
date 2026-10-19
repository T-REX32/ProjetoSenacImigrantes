package model;

public class Imigrante {
    private Long id;
    private Usuario usuario;
    private String paisOrigem;
    private String documento;
    private String telefone;
    private String escolaridade;
    private String experienciaProfissional;
    private String objetivoProfissional;

    public Imigrante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEscolaridade() { return escolaridade; }
    public void setEscolaridade(String escolaridade) { this.escolaridade = escolaridade; }
    public String getExperienciaProfissional() { return experienciaProfissional; }
    public void setExperienciaProfissional(String experienciaProfissional) { this.experienciaProfissional = experienciaProfissional; }
    public String getObjetivoProfissional() { return objetivoProfissional; }
    public void setObjetivoProfissional(String objetivoProfissional) { this.objetivoProfissional = objetivoProfissional; }
}
