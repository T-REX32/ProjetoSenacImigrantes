package modelo.entidade.dominio;

import java.time.LocalDate;
import java.util.UUID;

public class Certificado {
    private Long id;
    private String codigoValidacao;
    private LocalDate dataEmissao;
    private Long inscricaoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoValidacao() { return codigoValidacao; }
    public void setCodigoValidacao(String codigoValidacao) { this.codigoValidacao = codigoValidacao; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }
    public Long getInscricaoId() { return inscricaoId; }
    public void setInscricaoId(Long inscricaoId) { this.inscricaoId = inscricaoId; }

    public void emitir(Long inscricaoId) {
        this.inscricaoId = inscricaoId;
        this.codigoValidacao = UUID.randomUUID().toString();
        this.dataEmissao = LocalDate.now();
    }

    public boolean validar(String codigo) {
        return codigoValidacao != null && codigoValidacao.equals(codigo);
    }
}
