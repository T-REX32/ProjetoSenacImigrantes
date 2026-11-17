package modelo.entidade.dominio;

public class Progresso {
    private Long id;
    private Integer aulasConcluidas = 0;
    private Integer totalAulas = 0;
    private Double percentual = 0.0;
    private Long inscricaoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getAulasConcluidas() { return aulasConcluidas; }
    public void setAulasConcluidas(Integer aulasConcluidas) { this.aulasConcluidas = aulasConcluidas; }
    public Integer getTotalAulas() { return totalAulas; }
    public void setTotalAulas(Integer totalAulas) { this.totalAulas = totalAulas; }
    public Double getPercentual() { return percentual; }
    public void setPercentual(Double percentual) { this.percentual = percentual; }
    public Long getInscricaoId() { return inscricaoId; }
    public void setInscricaoId(Long inscricaoId) { this.inscricaoId = inscricaoId; }

    public void atualizar(int concluidas, int total) {
        aulasConcluidas = concluidas;
        totalAulas = total;
        percentual = total <= 0 ? 0.0 : Math.min(100.0, concluidas * 100.0 / total);
    }

    public void concluirAula() {
        atualizar(Math.min(aulasConcluidas + 1, totalAulas), totalAulas);
    }
}
