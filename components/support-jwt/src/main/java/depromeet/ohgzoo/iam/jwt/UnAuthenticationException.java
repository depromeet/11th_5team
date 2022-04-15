package depromeet.ohgzoo.iam.jwt;

public class UnAuthenticationException extends RuntimeException {
    public UnAuthenticationException(String message) {
        super(message);
    }
}
