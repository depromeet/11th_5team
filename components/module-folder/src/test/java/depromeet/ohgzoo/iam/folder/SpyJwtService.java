package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.jwt.JwtService;

public class SpyJwtService implements JwtService {
    public String getSubject_argumentToken;
    public String getSubject_returnValue = "1";

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
        getSubject_argumentToken = token;
        return getSubject_returnValue;
    }
}
