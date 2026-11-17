package modelo.entidade.usuario;

public class ProfessorVoluntario extends Usuario {
    private String areaConhecimento;
    private String experiencia;

    public String getAreaConhecimento() { return areaConhecimento; }
    public void setAreaConhecimento(String areaConhecimento) { this.areaConhecimento = areaConhecimento; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }
}
