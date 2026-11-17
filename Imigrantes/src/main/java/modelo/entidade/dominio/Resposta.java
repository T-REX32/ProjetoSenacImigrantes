package modelo.entidade.dominio;

import java.time.LocalDateTime;

public class Resposta {
    private Long id;
    private String conteudo;
    private LocalDateTime enviadaEm;
    private Long formularioId;
    private Long imigranteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getEnviadaEm() { return enviadaEm; }
    public void setEnviadaEm(LocalDateTime enviadaEm) { this.enviadaEm = enviadaEm; }
    public Long getFormularioId() { return formularioId; }
    public void setFormularioId(Long formularioId) { this.formularioId = formularioId; }
    public Long getImigranteId() { return imigranteId; }
    public void setImigranteId(Long imigranteId) { this.imigranteId = imigranteId; }
}
