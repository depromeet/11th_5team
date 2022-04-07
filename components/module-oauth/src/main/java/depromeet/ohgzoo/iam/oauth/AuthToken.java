package depromeet.ohgzoo.iam.oauth;

import lombok.Getter;

@Getter
public class AuthToken {
    private final String auth;

    public AuthToken(String auth) {
        this.auth = auth;
    }
}
