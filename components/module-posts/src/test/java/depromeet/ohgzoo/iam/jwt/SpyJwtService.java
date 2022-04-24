package depromeet.ohgzoo.iam.jwt;

public class SpyJwtService implements JwtService {
    public String getSubject_returnValue = "0";

    @Override
    public String issuedToken(String subject, String role, long periodSecond) {
        return null;
    }

    @Override
    public boolean verifyToken(String token) {
        return false;
    }

    @Override
    public String getSubject(String token) {
        return getSubject_returnValue;
    }
}
