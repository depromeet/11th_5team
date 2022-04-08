package depromeet.ohgzoo.iam.oauth.kakao;

public interface KakaoClient {
    KakaoTokenResponse getToken(KakaoTokenRequest request);
    KakaoProfileResponse getProfile(String authorization);
}
