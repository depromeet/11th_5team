package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.jwt.UnAuthenticationException;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoClient;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenRequest;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OauthServiceImpl implements OauthService {
    @Value("${kakaoClientId}")
    private String clientId;

    @Value("${kakaoRedirectUrl}")
    private String redirectUrl;

    private final KakaoClient kakaoClient;
    private final JwtService jwtService;

    @Override
    public Oauth2LoginUrl getLoginUrl() {
        return new Oauth2LoginUrl(
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

        return new AuthToken(
            jwtService.issuedToken(profile.getAccount().getEmail(), "USER", 3600),
            jwtService.issuedToken(profile.getAccount().getEmail(), "USER", 36000)
        );
    }

    @Override
    public AuthToken getRefreshToken(String refreshToken) {
        if (!jwtService.verifyToken(refreshToken)) {
            throw new UnAuthenticationException("토큰이 만료되었습니다.");
        }

        String email = jwtService.getSubject(refreshToken);
        return new AuthToken(
                jwtService.issuedToken(email, "USER", 3600),
                jwtService.issuedToken(email, "USER", 36000));
    }

    private KakaoProfileResponse getKakaoProfile(KakaoTokenResponse kakaoToken) {
        return kakaoClient.getProfile("Bearer " + kakaoToken.getAccessToken());
    }

    private KakaoTokenResponse getKakaoToken(String code) {
        KakaoTokenRequest kakaoTokenRequest = new KakaoTokenRequest(clientId, redirectUrl, code);
        return kakaoClient.getToken(kakaoTokenRequest);
    }
}

