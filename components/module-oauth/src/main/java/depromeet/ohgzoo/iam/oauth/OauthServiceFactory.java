package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.oauth.apple.AppleOauthService;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoOauthService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OauthServiceFactory {
    private static final Map<String, OauthService> authServiceMap = new HashMap<>();

    public OauthServiceFactory(final KakaoOauthService kakaoOauthService,
                               final AppleOauthService appleOauthService) {
        authServiceMap.put("kakao", kakaoOauthService);
        authServiceMap.put("apple", appleOauthService);
    }

    public OauthService getOauthService(final String provider) {
        return authServiceMap.get(provider);
    }
}
