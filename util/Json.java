package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Json {
    private Json() {
    }

    public static String gerar(Object valor) {
        if (valor == null) {
            return "null";
        }
        if (valor instanceof String texto) {
            return "\"" + escapar(texto) + "\"";
        }
        if (valor instanceof Number || valor instanceof Boolean) {
            return valor.toString();
        }
        if (valor instanceof LocalDate || valor instanceof LocalDateTime || valor instanceof LocalTime) {
            return gerar(valor.toString());
        }
        if (valor.getClass().getPackageName().equals("model")) {
            return gerarModelo(valor);
        }
        if (valor instanceof Map<?, ?> mapa) {
            StringBuilder json = new StringBuilder("{");
            boolean primeiro = true;
            for (Map.Entry<?, ?> entrada : mapa.entrySet()) {
                if (!(entrada.getKey() instanceof String chave)) {
                    throw new IllegalArgumentException("Chaves de objeto JSON devem ser texto.");
                }
                if (campoSensivel(chave)) {
                    continue;
                }
                if (!primeiro) {
                    json.append(',');
                }
                json.append(gerar(chave)).append(':').append(gerar(entrada.getValue()));
                primeiro = false;
            }
            return json.append('}').toString();
        }
        if (valor instanceof Iterable<?> lista) {
            StringBuilder json = new StringBuilder("[");
            boolean primeiro = true;
            for (Object item : lista) {
                if (!primeiro) {
                    json.append(',');
                }
                json.append(gerar(item));
                primeiro = false;
            }
            return json.append(']').toString();
        }
        throw new IllegalArgumentException("Tipo não suportado para JSON: " + valor.getClass().getName());
    }

    public static Object ler(String json) {
        Parser parser = new Parser(json);
        Object valor = parser.valor();
        parser.espacos();
        if (!parser.fim()) {
            throw parser.erro("Conteúdo extra após o JSON.");
        }
        return valor;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> lerObjeto(String json) {
        Object valor = ler(json);
        if (!(valor instanceof Map<?, ?>)) {
            throw new IllegalArgumentException("O JSON enviado deve ser um objeto.");
        }
        return (Map<String, Object>) valor;
    }

    public static LocalDate lerLocalDate(Object valor) {
        if (!(valor instanceof String texto)) {
            throw new IllegalArgumentException("A data deve ser um texto no formato AAAA-MM-DD.");
        }
        return LocalDate.parse(texto);
    }

    public static LocalDateTime lerLocalDateTime(Object valor) {
        if (!(valor instanceof String texto)) {
            throw new IllegalArgumentException("A data e hora devem ser um texto no formato ISO-8601.");
        }
        return LocalDateTime.parse(texto);
    }

    public static LocalTime lerLocalTime(Object valor) {
        if (!(valor instanceof String texto)) {
            throw new IllegalArgumentException("A hora deve ser um texto no formato HH:mm:ss.");
        }
        return LocalTime.parse(texto);
    }

    private static String gerarModelo(Object modelo) {
        Map<String, Object> propriedades = new LinkedHashMap<>();
        for (Method metodo : modelo.getClass().getMethods()) {
            if (!ehGetter(metodo)) {
                continue;
            }
            String nome = nomePropriedade(metodo);
            if (campoSensivel(nome)) {
                continue;
            }
            try {
                Object valor = metodo.invoke(modelo);
                if (valor != null && valor.getClass().getPackageName().equals("model")) {
                    propriedades.put(nome, referenciaModel(valor));
                } else {
                    propriedades.put(nome, valor);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("Não foi possível serializar o model " + modelo.getClass().getSimpleName(), e);
            }
        }
        return gerar(propriedades);
    }

    private static Map<String, Object> referenciaModel(Object modelo) {
        try {
            Object id = modelo.getClass().getMethod("getId").invoke(modelo);
            Map<String, Object> referencia = new LinkedHashMap<>();
            referencia.put("id", id);
            return referencia;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(
                    "Model relacionado sem getter getId(): " + modelo.getClass().getSimpleName(), e);
        }
    }

    private static boolean campoSensivel(String nome) {
        return nome.equalsIgnoreCase("senhaHash")
                || nome.equalsIgnoreCase("senha")
                || nome.equalsIgnoreCase("senha_hash")
                || nome.equalsIgnoreCase("tokenHash");
    }

    private static boolean ehGetter(Method metodo) {
        String nome = metodo.getName();
        return !Modifier.isStatic(metodo.getModifiers())
                && metodo.getParameterCount() == 0
                && metodo.getReturnType() != void.class
                && ((nome.startsWith("get") && nome.length() > 3 && !nome.equals("getClass"))
                || (nome.startsWith("is") && nome.length() > 2
                && (metodo.getReturnType() == boolean.class || metodo.getReturnType() == Boolean.class)));
    }

    private static String nomePropriedade(Method metodo) {
        String nome = metodo.getName().startsWith("get")
                ? metodo.getName().substring(3)
                : metodo.getName().substring(2);
        return Character.toLowerCase(nome.charAt(0)) + nome.substring(1);
    }

    private static String escapar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);
            switch (caractere) {
                case '"' -> resultado.append("\\\"");
                case '\\' -> resultado.append("\\\\");
                case '\b' -> resultado.append("\\b");
                case '\f' -> resultado.append("\\f");
                case '\n' -> resultado.append("\\n");
                case '\r' -> resultado.append("\\r");
                case '\t' -> resultado.append("\\t");
                default -> {
                    if (caractere < 0x20) {
                        resultado.append(String.format("\\u%04x", (int) caractere));
                    } else {
                        resultado.append(caractere);
                    }
                }
            }
        }
        return resultado.toString();
    }

    private static final class Parser {
        private final String texto;
        private int posicao;

        private Parser(String texto) {
            this.texto = texto;
        }

        private Object valor() {
            return valor(1);
        }

        private Object valor(int nivel) {
            espacos();
            if (fim()) {
                throw erro("JSON vazio.");
            }
            return switch (texto.charAt(posicao)) {
                case '{' -> objeto(nivel);
                case '[' -> lista(nivel);
                case '"' -> string();
                case 't' -> literal("true", Boolean.TRUE);
                case 'f' -> literal("false", Boolean.FALSE);
                case 'n' -> literal("null", null);
                default -> numero();
            };
        }

        private Map<String, Object> objeto(int nivel) {
            validarNivel(nivel);
            posicao++;
            Map<String, Object> objeto = new LinkedHashMap<>();
            espacos();
            if (consumir('}')) {
                return objeto;
            }
            while (true) {
                espacos();
                if (fim() || texto.charAt(posicao) != '"') {
                    throw erro("Esperada uma chave de texto.");
                }
                String chave = string();
                espacos();
                exigir(':');
                objeto.put(chave, valor(nivel + 1));
                espacos();
                if (consumir('}')) {
                    return objeto;
                }
                exigir(',');
            }
        }

        private List<Object> lista(int nivel) {
            validarNivel(nivel);
            posicao++;
            List<Object> lista = new ArrayList<>();
            espacos();
            if (consumir(']')) {
                return lista;
            }
            while (true) {
                lista.add(valor(nivel + 1));
                espacos();
                if (consumir(']')) {
                    return lista;
                }
                exigir(',');
            }
        }

        private String string() {
            exigir('"');
            StringBuilder resultado = new StringBuilder();
            while (!fim()) {
                char caractere = texto.charAt(posicao++);
                if (caractere == '"') {
                    return resultado.toString();
                }
                if (caractere < 0x20) {
                    throw erro("Caractere de controle inválido em texto.");
                }
                if (caractere != '\\') {
                    resultado.append(caractere);
                    continue;
                }
                if (fim()) {
                    throw erro("Escape incompleto.");
                }
                char escape = texto.charAt(posicao++);
                switch (escape) {
                    case '"', '\\', '/' -> resultado.append(escape);
                    case 'b' -> resultado.append('\b');
                    case 'f' -> resultado.append('\f');
                    case 'n' -> resultado.append('\n');
                    case 'r' -> resultado.append('\r');
                    case 't' -> resultado.append('\t');
                    case 'u' -> resultado.append(unicode());
                    default -> throw erro("Escape inválido.");
                }
            }
            throw erro("Texto não terminado.");
        }

        private char unicode() {
            if (posicao + 4 > texto.length()) {
                throw erro("Escape Unicode incompleto.");
            }
            try {
                char caractere = (char) Integer.parseInt(texto.substring(posicao, posicao + 4), 16);
                posicao += 4;
                return caractere;
            } catch (NumberFormatException e) {
                throw erro("Escape Unicode inválido.");
            }
        }

        private Number numero() {
            int inicio = posicao;
            consumir('-');
            if (consumir('0')) {
                if (!fim() && Character.isDigit(texto.charAt(posicao))) {
                    throw erro("Número inválido.");
                }
            } else if (!consumirDigitos()) {
                throw erro("Número inválido.");
            }
            boolean decimal = false;
            if (consumir('.')) {
                decimal = true;
                if (!consumirDigitos()) {
                    throw erro("Número inválido.");
                }
            }
            if (consumir('e') || consumir('E')) {
                decimal = true;
                if (!consumir('+')) {
                    consumir('-');
                }
                if (!consumirDigitos()) {
                    throw erro("Número inválido.");
                }
            }
            String numero = texto.substring(inicio, posicao);
            try {
                if (decimal) {
                    return Double.valueOf(numero);
                }
                return Long.valueOf(numero);
            } catch (NumberFormatException e) {
                throw erro("Número fora do intervalo suportado.");
            }
        }

        private boolean consumirDigitos() {
            int inicio = posicao;
            while (!fim() && Character.isDigit(texto.charAt(posicao))) {
                posicao++;
            }
            return posicao > inicio;
        }

        private Object literal(String literal, Object valor) {
            if (!texto.startsWith(literal, posicao)) {
                throw erro("Valor JSON inválido.");
            }
            posicao += literal.length();
            return valor;
        }

        private void espacos() {
            while (!fim()) {
                char caractere = texto.charAt(posicao);
                if (caractere != ' ' && caractere != '\t' && caractere != '\n' && caractere != '\r') {
                    return;
                }
                posicao++;
            }
        }

        private boolean consumir(char esperado) {
            if (!fim() && texto.charAt(posicao) == esperado) {
                posicao++;
                return true;
            }
            return false;
        }

        private void exigir(char esperado) {
            if (!consumir(esperado)) {
                throw erro("Esperado '" + esperado + "'.");
            }
        }

        private boolean fim() {
            return posicao >= texto.length();
        }

        private IllegalArgumentException erro(String mensagem) {
            return new IllegalArgumentException(mensagem + " Posição: " + posicao);
        }

        private void validarNivel(int nivel) {
            if (nivel > 50) {
                throw erro("O JSON excede o limite de 50 níveis de aninhamento.");
            }
        }
    }
}
