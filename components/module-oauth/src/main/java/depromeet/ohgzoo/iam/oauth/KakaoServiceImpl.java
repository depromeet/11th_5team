package depromeet.ohgzoo.iam.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {
    @Value("${kakaoClientId}")
    private String clientId;

    @Value("${kakaoRedirectUrl}")
    private String redirectUrl;

    private final KakaoClient kakaoClient;

    @Override
    public OAuth2LoginUrl getLoginUrl() {
        return new OAuth2LoginUrl(
                new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                        .append("?client_id=").append(clientId)
                        .append("&response_type=code")
                        .append("&redirect_uri=").append(redirectUrl)
                        .toString()
        );
    }

    @Override
    public AuthToken getToken(String code) {
        kakaoClient.getToken(code);

        return null;
    }
}

