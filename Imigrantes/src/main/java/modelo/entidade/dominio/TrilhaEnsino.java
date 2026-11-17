package modelo.entidade.dominio;

public class TrilhaEnsino {
    private Long id;
    private String titulo;
    private String descricao;
    private Integer cargaHorariaMinutos;
    private boolean publicada;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getCargaHorariaMinutos() { return cargaHorariaMinutos; }
    public void setCargaHorariaMinutos(Integer cargaHorariaMinutos) { this.cargaHorariaMinutos = cargaHorariaMinutos; }
    public boolean isPublicada() { return publicada; }
    public void setPublicada(boolean publicada) { this.publicada = publicada; }
}
