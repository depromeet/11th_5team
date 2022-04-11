package depromeet.ohgzoo.iam.security.dto;

import lombok.Getter;

@Getter
public class AuthToken {
    private final String auth;
    private final String refresh;

    public AuthToken(String auth, String refresh) {
        this.auth = auth;
        this.refresh = refresh;
    }
}