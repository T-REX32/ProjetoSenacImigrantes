package modelo.entidade.dominio;

import java.time.LocalDateTime;

public class Anuncio {
    private Long id;
    private Long aulaId;
    private String titulo;
    private String descricao;
    private LocalDateTime publicadoEm;
    private boolean ativo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAulaId() { return aulaId; }
    public void setAulaId(Long aulaId) { this.aulaId = aulaId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getPublicadoEm() { return publicadoEm; }
    public void setPublicadoEm(LocalDateTime publicadoEm) { this.publicadoEm = publicadoEm; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
