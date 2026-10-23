package model;

public class Progresso {
    private Long id;
    private Inscricao inscricao;
    private Integer aulasConcluidas;
    private Integer totalAulas;
    private Double percentual;

    public Progresso() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Inscricao getInscricao() { return inscricao; }
    public void setInscricao(Inscricao inscricao) { this.inscricao = inscricao; }
    public Integer getAulasConcluidas() { return aulasConcluidas; }
    public void setAulasConcluidas(Integer aulasConcluidas) { this.aulasConcluidas = aulasConcluidas; }
    public Integer getTotalAulas() { return totalAulas; }
    public void setTotalAulas(Integer totalAulas) { this.totalAulas = totalAulas; }
    public Double getPercentual() { return percentual; }
    public void setPercentual(Double percentual) { this.percentual = percentual; }
}
