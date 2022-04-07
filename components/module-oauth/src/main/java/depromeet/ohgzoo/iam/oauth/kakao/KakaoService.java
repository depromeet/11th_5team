package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.oauth.AuthToken;
import depromeet.ohgzoo.iam.oauth.OAuth2LoginUrl;

public interface KakaoService {
    OAuth2LoginUrl getLoginUrl();

    AuthToken getToken(String code);
}
