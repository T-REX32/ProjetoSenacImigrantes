package model;

public class Voluntario {
    private Long id;
    private Usuario usuario;
    private String areaConhecimento;
    private String competencias;
    private String disponibilidade;

    public Voluntario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getAreaConhecimento() { return areaConhecimento; }
    public void setAreaConhecimento(String areaConhecimento) { this.areaConhecimento = areaConhecimento; }
    public String getCompetencias() { return competencias; }
    public void setCompetencias(String competencias) { this.competencias = competencias; }
    public String getDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(String disponibilidade) { this.disponibilidade = disponibilidade; }
}
