package depromeet.ohgzoo.iam.oauth;

public interface OauthService {

    AuthToken getToken(String code);

    AuthToken getRefreshToken(String refreshToken);
}
