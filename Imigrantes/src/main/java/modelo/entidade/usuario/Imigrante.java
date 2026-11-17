package modelo.entidade.usuario;

public class Imigrante extends Usuario {
    private String paisOrigem;
    private String idiomaPreferido;
    private String statusMigratorio;

    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }

    public String getIdiomaPreferido() { return idiomaPreferido; }
    public void setIdiomaPreferido(String idiomaPreferido) { this.idiomaPreferido = idiomaPreferido; }

    public String getStatusMigratorio() { return statusMigratorio; }
    public void setStatusMigratorio(String statusMigratorio) { this.statusMigratorio = statusMigratorio; }
}
