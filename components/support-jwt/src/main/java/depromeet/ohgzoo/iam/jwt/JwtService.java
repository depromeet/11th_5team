package depromeet.ohgzoo.iam.jwt;

import java.security.PublicKey;

public interface JwtService {
    String issuedToken(String subject, String role, long periodSecond);

    boolean verifyToken(String token);

    String getSubject(String token);

    String getSubjectBySigningKey(String token, PublicKey publicKey);
}
