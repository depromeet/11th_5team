package depromeet.ohgzoo.iam.oauth;

public class SpyKakaoService implements KakaoService {
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
