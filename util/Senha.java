package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Senha {
    private static final int ITERACOES = 120_000;
    private static final int TAMANHO_SALT = 16;
    private static final int TAMANHO_HASH_BITS = 256;
    private static final SecureRandom ALEATORIO = new SecureRandom();

    public String gerarHash(String senha) {
        byte[] salt = new byte[TAMANHO_SALT];
        ALEATORIO.nextBytes(salt);
        byte[] hash = derivar(senha.toCharArray(), salt, ITERACOES);
        return ITERACOES + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    public boolean verificar(String senha, String hashArmazenado) {
        if (senha == null || hashArmazenado == null) {
            return false;
        }
        try {
            String[] partes = hashArmazenado.split(":", -1);
            if (partes.length != 3) {
                return false;
            }
            int iteracoes = Integer.parseInt(partes[0]);
            if (iteracoes <= 0) {
                return false;
            }
            byte[] salt = Base64.getDecoder().decode(partes[1]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[2]);
            byte[] hashRecebido = derivar(senha.toCharArray(), salt, iteracoes);
            return MessageDigest.isEqual(hashEsperado, hashRecebido);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derivar(char[] senha, byte[] salt, int iteracoes) {
        PBEKeySpec especificacao = new PBEKeySpec(senha, salt, iteracoes, TAMANHO_HASH_BITS);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(especificacao)
                    .getEncoded();
        } catch (java.security.GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível processar a senha com PBKDF2.", e);
        } finally {
            especificacao.clearPassword();
            java.util.Arrays.fill(senha, '\0');
        }
    }
}
