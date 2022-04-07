package depromeet.ohgzoo.iam.oauth.kakao;

public interface KakaoClient {
    KakaoTokenResponse getToken(KakaoTokenRequest request);
    String getProfile(String authorization);
}
