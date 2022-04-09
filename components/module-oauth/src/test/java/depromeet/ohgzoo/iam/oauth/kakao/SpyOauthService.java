package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.oauth.AuthToken;
import depromeet.ohgzoo.iam.oauth.Oauth2LoginUrl;
import depromeet.ohgzoo.iam.oauth.OauthService;

public class SpyOauthService implements OauthService {
    public Oauth2LoginUrl getLoginUrl_returnValue;
    public String getToken_argumentCode;
    public AuthToken getToken_returnValue;

    @Override
    public Oauth2LoginUrl getLoginUrl() {
        return getLoginUrl_returnValue;
    }

    @Override
    public AuthToken getToken(String code) {
        getToken_argumentCode = code;
        return getToken_returnValue;
    }
}
