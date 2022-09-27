package depromeet.ohgzoo.iam.oauth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class AppleTokenDecoderImpl implements AppleTokenDecoder {

    private final ObjectMapper objectMapper;
    private final AppleApiClient appleApiClient;
    private final JwtService jwtService;

    @Override
    public AppleProfile getProfile(final String code) {
        final String headerToken = code.split("\\.")[0];
        final AppleHeader appleHeader = convertToAppleHeader(headerToken);
        final ApplePublicKeyResponse response = appleApiClient.getAppleAuthPublicKey();
        final ApplePublicKeyResponse.Key key = response.getMatchedPublicKey(appleHeader);

        final byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
        final byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

        final BigInteger n = new BigInteger(1, nBytes);
        final BigInteger e = new BigInteger(1, eBytes);

        final RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            final PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            final String appleId = jwtService.getSubjectBySigningKey(code, publicKey);

            return new AppleProfile(appleId);
        } catch (final NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    private AppleHeader convertToAppleHeader(final String headerToken) {
        try {
            return  objectMapper.readValue(
                    new String(Base64.getDecoder().decode(headerToken),
                            StandardCharsets.UTF_8), AppleHeader.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
