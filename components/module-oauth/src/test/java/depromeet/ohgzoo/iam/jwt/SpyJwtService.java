package depromeet.ohgzoo.iam.jwt;

import java.util.LinkedList;
import java.util.Queue;

public class SpyJwtService implements JwtService {
    private Queue<String> issuedToken_argumentSubject = new LinkedList<>();
    private Queue<String> issuedToken_argumentRole = new LinkedList<>();
    private Queue<Long> issuedToken_argumentPeriod = new LinkedList<>();
    public int issuedToken_callCount = 0;

    @Override
    public String issuedToken(String subject, String role, long periodSecond) {
        issuedToken_callCount++;
        issuedToken_argumentSubject.add(subject);
        issuedToken_argumentRole.add(role);
        issuedToken_argumentPeriod.add(periodSecond);
        return null;
    }

    @Override
    public boolean verifyToken(String token) {
        return false;
    }

    @Override
    public String getSubject(String token) {
        return null;
    }

    public String getIssuedToken_argumentSubject() {
        return issuedToken_argumentSubject.poll();
    }

    public String getIssuedToken_argumentRole() {
        return issuedToken_argumentRole.poll();
    }

    public Long getIssuedToken_argumentPeriod() {
        return issuedToken_argumentPeriod.poll();
    }
}
