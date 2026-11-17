package modelo.entidade.usuario;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String senhaHash;
    private String senhaSalt;
    private StatusUsuario status = StatusUsuario.ATIVO;

    public Usuario() {}

    public Usuario(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getSenhaSalt() { return senhaSalt; }
    public void setSenhaSalt(String senhaSalt) { this.senhaSalt = senhaSalt; }

    public StatusUsuario getStatus() { return status; }
    public void setStatus(StatusUsuario status) { this.status = status; }
}