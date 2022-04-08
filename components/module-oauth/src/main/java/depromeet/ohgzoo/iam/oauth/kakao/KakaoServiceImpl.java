package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.oauth.AuthToken;
import depromeet.ohgzoo.iam.oauth.OAuth2LoginUrl;
import depromeet.ohgzoo.iam.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements OAuthService {
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
        KakaoTokenResponse kakaoToken = getKakaoToken(code);
        KakaoProfileResponse profile = getKakaoProfile(kakaoToken);

        return null;
    }

    private KakaoProfileResponse getKakaoProfile(KakaoTokenResponse kakaoToken) {
        return kakaoClient.getProfile("Bearer " + kakaoToken.getAccessToken());
    }

    private KakaoTokenResponse getKakaoToken(String code) {
        KakaoTokenRequest kakaoTokenRequest = new KakaoTokenRequest(clientId, redirectUrl, code);
        return kakaoClient.getToken(kakaoTokenRequest);
    }
}

