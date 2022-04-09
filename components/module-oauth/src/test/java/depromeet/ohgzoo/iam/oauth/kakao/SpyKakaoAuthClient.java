package depromeet.ohgzoo.iam.oauth.kakao;

public class SpyKakaoAuthClient implements KakaoAuthClient {
    public KakaoTokenRequest getToken_argumentCode;
    public KakaoTokenResponse getToken_returnValue = new KakaoTokenResponse(null, null, null, 0, null);
    public String getProfile_argumentAuthorization;

    @Override
    public KakaoTokenResponse getToken(KakaoTokenRequest request) {
        getToken_argumentCode = request;
        return getToken_returnValue;
    }
}
