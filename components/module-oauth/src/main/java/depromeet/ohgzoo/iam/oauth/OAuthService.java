package depromeet.ohgzoo.iam.oauth;

public interface OAuthService {
    OAuth2LoginUrl getLoginUrl();

    AuthToken getToken(String code);
}
