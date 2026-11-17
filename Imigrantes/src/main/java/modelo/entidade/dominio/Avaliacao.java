package modelo.entidade.dominio;

public class Avaliacao {
    private Long id;
    private Integer nota;
    private String comentario;
    private Long imigranteId;
    private Long aulaId;
    private Long professorFormadoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Long getImigranteId() { return imigranteId; }
    public void setImigranteId(Long imigranteId) { this.imigranteId = imigranteId; }
    public Long getAulaId() { return aulaId; }
    public void setAulaId(Long aulaId) { this.aulaId = aulaId; }
    public Long getProfessorFormadoId() { return professorFormadoId; }
    public void setProfessorFormadoId(Long professorFormadoId) { this.professorFormadoId = professorFormadoId; }
}
