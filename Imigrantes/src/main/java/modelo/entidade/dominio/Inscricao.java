package modelo.entidade.dominio;

import java.time.LocalDate;

import modelo.entidade.usuario.StatusInscricao;

public class Inscricao {
    private Long id;
    private LocalDate dataInscricao;
    private StatusInscricao status = StatusInscricao.PENDENTE;
    private Long imigranteId;
    private Long trilhaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(LocalDate dataInscricao) { this.dataInscricao = dataInscricao; }
    public StatusInscricao getStatus() { return status; }
    public void setStatus(StatusInscricao status) { this.status = status; }
    public Long getImigranteId() { return imigranteId; }
    public void setImigranteId(Long imigranteId) { this.imigranteId = imigranteId; }
    public Long getTrilhaId() { return trilhaId; }
    public void setTrilhaId(Long trilhaId) { this.trilhaId = trilhaId; }

    public void confirmar() { status = StatusInscricao.ATIVA; }
    public void cancelar() { status = StatusInscricao.CANCELADA; }
    public void concluir() { status = StatusInscricao.CONCLUIDA; }
    public boolean podeGerarCertificado() { return status == StatusInscricao.CONCLUIDA; }
}
