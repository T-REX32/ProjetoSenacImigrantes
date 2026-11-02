package model;

public class ProfessorFormado {
    private Long id;
    private String formacao;
    private String especialidade;

    public ProfessorFormado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
