package model;

import java.time.LocalDate;

public class Certificado {
    private Long id;
    private Inscricao inscricao;
    private String codigoValidacao;
    private LocalDate dataEmissao;

    public Certificado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Inscricao getInscricao() { return inscricao; }
    public void setInscricao(Inscricao inscricao) { this.inscricao = inscricao; }
    public String getCodigoValidacao() { return codigoValidacao; }
    public void setCodigoValidacao(String codigoValidacao) { this.codigoValidacao = codigoValidacao; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }
}
