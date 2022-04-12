package depromeet.ohgzoo.iam.oauth;

public interface OauthService {
    Oauth2LoginUrl getLoginUrl();

    AuthToken getToken(String code);

    AuthToken getRefreshToken(String refreshToken);
}
