package model;

import java.time.LocalDateTime;

public class Mensagem {
    private Long id;
    private Usuario remetente;
    private Usuario destinatario;
    private String texto;
    private LocalDateTime dataEnvio;
    private Boolean lida;

    public Mensagem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getRemetente() { return remetente; }
    public void setRemetente(Usuario remetente) { this.remetente = remetente; }
    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
    public Boolean getLida() { return lida; }
    public void setLida(Boolean lida) { this.lida = lida; }
}
