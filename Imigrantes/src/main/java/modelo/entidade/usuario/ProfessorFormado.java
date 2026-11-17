package modelo.entidade.usuario;

public class ProfessorFormado extends Usuario {
    private String formacao;
    private String especialidade;

    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
