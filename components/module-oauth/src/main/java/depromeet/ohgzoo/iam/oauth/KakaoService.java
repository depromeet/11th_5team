package depromeet.ohgzoo.iam.oauth;

public interface KakaoService {
    OAuth2LoginUrl getLoginUrl();

    AuthToken getToken(String code);
}
