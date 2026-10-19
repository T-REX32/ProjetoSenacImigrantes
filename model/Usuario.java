package model;

import java.time.LocalDate;

public class Usuario {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String senhaHash;
    private String tipoUsuario;
    private String status;
    private Idioma idioma;
    private Boolean notificacoesAtivas;
    private LocalDate dataCadastro;

    public Usuario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Idioma getIdioma() { return idioma; }
    public void setIdioma(Idioma idioma) { this.idioma = idioma; }
    public Boolean getNotificacoesAtivas() { return notificacoesAtivas; }
    public void setNotificacoesAtivas(Boolean notificacoesAtivas) { this.notificacoesAtivas = notificacoesAtivas; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
}
