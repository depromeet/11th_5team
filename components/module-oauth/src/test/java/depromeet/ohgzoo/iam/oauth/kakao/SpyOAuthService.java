package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.oauth.AuthToken;
import depromeet.ohgzoo.iam.oauth.OAuth2LoginUrl;
import depromeet.ohgzoo.iam.oauth.OAuthService;

public class SpyOAuthService implements OAuthService {
    public OAuth2LoginUrl getLoginUrl_returnValue;
    public String getToken_argumentCode;
    public AuthToken getToken_returnValue;

    @Override
    public OAuth2LoginUrl getLoginUrl() {
        return getLoginUrl_returnValue;
    }

    @Override
    public AuthToken getToken(String code) {
        getToken_argumentCode = code;
        return getToken_returnValue;
    }
}
