package modelo.dao.dominio;

import java.util.List;

public class ModuloDominio {
    private final String codigo;
    private final String rotulo;
    private final String tabela;
    private final String chave;
    private final boolean somenteLeitura;
    private final List<String> colunas;
    private final List<CampoDominio> campos;

    public ModuloDominio(String codigo, String rotulo, String tabela, String chave,
            boolean somenteLeitura, List<String> colunas, List<CampoDominio> campos) {
        this.codigo = codigo;
        this.rotulo = rotulo;
        this.tabela = tabela;
        this.chave = chave;
        this.somenteLeitura = somenteLeitura;
        this.colunas = colunas;
        this.campos = campos;
    }

    public String getCodigo() { return codigo; }
    public String getRotulo() { return rotulo; }
    public String getTabela() { return tabela; }
    public String getChave() { return chave; }
    public boolean isSomenteLeitura() { return somenteLeitura; }
    public List<String> getColunas() { return colunas; }
    public List<CampoDominio> getCampos() { return campos; }

    public String getRotuloColuna(String coluna) {
        if ("id".equals(coluna)) {
            return "ID";
        }
        String rotulo = coluna.replace('_', ' ');
        return rotulo.substring(0, 1).toUpperCase() + rotulo.substring(1);
    }
}
