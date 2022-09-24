package depromeet.ohgzoo.iam.oauth;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OauthServiceFactory {
    private static final Map<String, OauthService> authServiceMap = new HashMap<>();

    public OauthServiceFactory(final OauthService kakaoOauthService) {
        authServiceMap.put("kakao", kakaoOauthService);
    }

    public OauthService getOauthService(final String provider) {
        return authServiceMap.get(provider);
    }
}
