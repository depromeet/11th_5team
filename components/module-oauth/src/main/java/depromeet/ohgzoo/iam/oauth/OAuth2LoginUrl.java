package depromeet.ohgzoo.iam.oauth;

import lombok.Getter;

@Getter
public class OAuth2LoginUrl {
    private final String loginUrl;

    public OAuth2LoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
