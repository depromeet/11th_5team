package depromeet.ohgzoo.iam.oauth.apple;

import depromeet.ohgzoo.iam.oauth.AuthToken;
import depromeet.ohgzoo.iam.oauth.OauthService;
import org.springframework.stereotype.Service;

@Service
public class AppleOauthService implements OauthService {

    @Override
    public AuthToken getToken(String code) {
        return null;
    }

    @Override
    public AuthToken getRefreshToken(String refreshToken) {
        return null;
    }
}
