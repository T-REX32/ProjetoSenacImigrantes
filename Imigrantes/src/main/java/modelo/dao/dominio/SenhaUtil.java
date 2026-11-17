package modelo.dao.dominio;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class SenhaUtil {
    private static final int ITERACOES = 65536;
    private static final int TAMANHO_CHAVE = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private SenhaUtil() {
    }

    public static String gerarSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String gerarHash(String senha, String salt) {
        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(),
                Base64.getDecoder().decode(salt), ITERACOES, TAMANHO_CHAVE);
        try {
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Nao foi possivel proteger a senha.", e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean verificar(String senha, String salt, String hashEsperado) {
        if (hashEsperado == null || salt == null) {
            return false;
        }
        byte[] esperado = Base64.getDecoder().decode(hashEsperado);
        byte[] calculado = Base64.getDecoder().decode(gerarHash(senha, salt));
        return MessageDigest.isEqual(esperado, calculado);
    }
}
