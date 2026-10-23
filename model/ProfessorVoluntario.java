package model;

public class ProfessorVoluntario {
    private Long id;
    private String areaConhecimento;
    private String experiencia;

    public ProfessorVoluntario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAreaConhecimento() { return areaConhecimento; }
    public void setAreaConhecimento(String areaConhecimento) { this.areaConhecimento = areaConhecimento; }
    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }
}
