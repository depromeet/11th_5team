package depromeet.ohgzoo.iam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class DecryptConverterTest {

    private DecryptConverter sut;

    String key = "abcdefghabcdefghabcdefghabcdefgh";
    String iv = "0123456789abcdef";

    @BeforeEach
    void setUp() {
        sut = new DecryptConverter();
    }

    @Test
    void 암호화된_문자열을_반환한다() throws Exception {

        String result = sut.convertToDatabaseColumn("hello");

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = cipher.doFinal("hello".getBytes(StandardCharsets.UTF_8));
        String expected = Base64.getEncoder().encodeToString(encrypted);

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void 복호화된_문자열을_반환한다() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode("Pzi2tU1B1R+z6Pdxh2xR3A==");
        byte[] decrypted = cipher.doFinal(decodedBytes);
        String expected = new String(decrypted, "UTF-8");

        String result = sut.convertToEntityAttribute("Pzi2tU1B1R+z6Pdxh2xR3A==");

        assertThat(expected).isEqualTo(result);
    }
}