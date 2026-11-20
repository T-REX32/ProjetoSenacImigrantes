package controle.seguranca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelo.dao.dominio.ModuloDominio;

public final class PermissaoUsuario {
    private static final Map<String, Set<String>> MODULOS = new HashMap<>();
    private static final Map<String, Set<String>> EDICAO = new HashMap<>();

    static {
        MODULOS.put("IMIGRANTE", conjunto("trilhas", "aulas", "anuncios"));
        MODULOS.put("PROFESSOR_FORMADO", conjunto("professores-voluntarios", "trilhas"));
        MODULOS.put("PROFESSOR_VOLUNTARIO", conjunto("aulas", "salas", "horarios"));
        MODULOS.put("REPRESENTANTE", conjunto("salas", "horarios"));

        EDICAO.put("PROFESSOR_FORMADO", conjunto("trilhas"));
        EDICAO.put("PROFESSOR_VOLUNTARIO", conjunto("aulas"));
        EDICAO.put("REPRESENTANTE", conjunto("salas", "horarios"));
    }

    private PermissaoUsuario() {
    }

    public static boolean podeAcessarModulo(String perfil, String modulo) {
        return "ADMINISTRADOR".equals(perfil)
                || MODULOS.containsKey(perfil) && MODULOS.get(perfil).contains(modulo);
    }

    public static boolean podeEditarModulo(String perfil, String modulo, long id) {
        if ("ADMINISTRADOR".equals(perfil)) {
            return true;
        }
        if ("PROFESSOR_FORMADO".equals(perfil) && "trilhas".equals(modulo)) {
            return true;
        }
        return id == 0 && EDICAO.containsKey(perfil) && EDICAO.get(perfil).contains(modulo);
    }

    public static boolean podeAcessarCurso(String perfil) {
        return "ADMINISTRADOR".equals(perfil) || "PROFESSOR_FORMADO".equals(perfil);
    }

    public static boolean podeAcessarAulas(String perfil) {
        return "ADMINISTRADOR".equals(perfil) || "PROFESSOR_FORMADO".equals(perfil)
                || "PROFESSOR_VOLUNTARIO".equals(perfil);
    }

    public static boolean podeCriarConta(String perfil) {
        return "IMIGRANTE".equals(perfil) || "PROFESSOR_FORMADO".equals(perfil)
                || "PROFESSOR_VOLUNTARIO".equals(perfil) || "REPRESENTANTE".equals(perfil);
    }

    public static List<ModuloDominio> filtrarModulos(String perfil, List<ModuloDominio> modulos) {
        if ("ADMINISTRADOR".equals(perfil)) {
            return modulos;
        }
        List<ModuloDominio> permitidos = new ArrayList<>();
        for (ModuloDominio modulo : modulos) {
            if (podeAcessarModulo(perfil, modulo.getCodigo())) {
                permitidos.add(modulo);
            }
        }
        return Collections.unmodifiableList(permitidos);
    }

    private static Set<String> conjunto(String... modulos) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(modulos)));
    }
}
