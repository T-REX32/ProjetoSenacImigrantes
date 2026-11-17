package modelo.dao.dominio;

import java.util.ArrayList;
import java.util.List;

public class CampoDominio {
    private final String nome;
    private final String rotulo;
    private final String tipo;
    private final boolean obrigatorio;
    private final String consultaOpcoes;
    private final List<String> valores;

    public CampoDominio(String nome, String rotulo, String tipo, boolean obrigatorio,
            String consultaOpcoes, List<String> valores) {
        this.nome = nome;
        this.rotulo = rotulo;
        this.tipo = tipo;
        this.obrigatorio = obrigatorio;
        this.consultaOpcoes = consultaOpcoes;
        this.valores = valores == null ? new ArrayList<String>() : valores;
    }

    public String getNome() { return nome; }
    public String getRotulo() { return rotulo; }
    public String getTipo() { return tipo; }
    public boolean isObrigatorio() { return obrigatorio; }
    public String getConsultaOpcoes() { return consultaOpcoes; }
    public List<String> getValores() { return valores; }
}
