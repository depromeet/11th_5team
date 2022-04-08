package depromeet.ohgzoo.iam.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoClientAdapter implements KakaoClient {
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoApiClient kakaoApiClient;

    @Override
    public KakaoTokenResponse getToken(KakaoTokenRequest request) {
        return kakaoAuthClient.getToken(request);
    }

    @Override
    public KakaoProfileResponse getProfile(String authorization) {
        KakaoProfileResponse profile = kakaoApiClient.getProfile(authorization);
        System.out.println("profile = " + profile);
        return profile;
    }
}
