package modelo.entidade.dominio;

import java.time.LocalDate;

public class Presenca {
    private Long id;
    private Long aulaId;
    private Long imigranteId;
    private LocalDate data;
    private boolean presente;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAulaId() { return aulaId; }
    public void setAulaId(Long aulaId) { this.aulaId = aulaId; }
    public Long getImigranteId() { return imigranteId; }
    public void setImigranteId(Long imigranteId) { this.imigranteId = imigranteId; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public boolean isPresente() { return presente; }
    public void setPresente(boolean presente) { this.presente = presente; }
}
