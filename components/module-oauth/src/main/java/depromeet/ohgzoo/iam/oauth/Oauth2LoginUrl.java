package depromeet.ohgzoo.iam.oauth;

import lombok.Getter;

@Getter
public class Oauth2LoginUrl {
    private final String loginUrl;

    public Oauth2LoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
