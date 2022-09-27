package depromeet.ohgzoo.iam.oauth;

public class SpyOauthService implements OauthService {
    public Oauth2LoginUrl getLoginUrl_returnValue;
    public String getToken_argumentCode;
    public AuthToken getToken_returnValue;
    public String getRefreshToken_argumentRefreshToken;
    public AuthToken getRefreshToken_returnValue;

    @Override
    public AuthToken getToken(String code) {
        getToken_argumentCode = code;
        return getToken_returnValue;
    }

    @Override
    public AuthToken getRefreshToken(String refreshToken) {
        getRefreshToken_argumentRefreshToken = refreshToken;
        return getRefreshToken_returnValue;
    }
}
