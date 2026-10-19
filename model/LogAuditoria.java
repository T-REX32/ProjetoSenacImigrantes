package model;

import java.time.LocalDateTime;

public class LogAuditoria {
    private Long id;
    private Usuario usuario;
    private String campoAlterado;
    private LocalDateTime dataHora;

    public LogAuditoria() {}

    public Long getId() {
         return id; 
    }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getCampoAlterado() { return campoAlterado; }
    public void setCampoAlterado(String campoAlterado) { this.campoAlterado = campoAlterado; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
