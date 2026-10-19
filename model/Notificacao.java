package model;

import java.time.LocalDateTime;

public class Notificacao {
    private Long id;
    private Usuario usuario;
    private String tipo;
    private String mensagem;
    private Boolean lida;
    private LocalDateTime dataCriacao;

    public Notificacao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public Boolean getLida() { return lida; }
    public void setLida(Boolean lida) { this.lida = lida; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
