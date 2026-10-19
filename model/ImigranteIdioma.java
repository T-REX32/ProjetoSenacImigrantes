package model;

public class ImigranteIdioma {
    private Long id;
    private Imigrante imigrante;
    private Idioma idioma;
    private Boolean nativo;

    public ImigranteIdioma() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Imigrante getImigrante() { return imigrante; }
    public void setImigrante(Imigrante imigrante) { this.imigrante = imigrante; }
    public Idioma getIdioma() { return idioma; }
    public void setIdioma(Idioma idioma) { this.idioma = idioma; }
    public Boolean getNativo() { return nativo; }
    public void setNativo(Boolean nativo) { this.nativo = nativo; }
}
