package model;

import java.time.LocalDateTime;

public class TokenRecuperacaoSenha {
    private Long id;
    private Usuario usuario;
    private String tokenHash;
    private LocalDateTime dataExpiracao;
    private Boolean usado;

    public TokenRecuperacaoSenha() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getTokenHash() { return tokenHash; }
    public void setTokenHash(String tokenHash) { this.tokenHash = tokenHash; }
    public LocalDateTime getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(LocalDateTime dataExpiracao) { this.dataExpiracao = dataExpiracao; }
    public Boolean getUsado() { return usado; }
    public void setUsado(Boolean usado) { this.usado = usado; }
}
