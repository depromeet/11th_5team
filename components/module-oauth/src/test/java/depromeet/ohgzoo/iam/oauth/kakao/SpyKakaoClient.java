package depromeet.ohgzoo.iam.oauth.kakao;

public class SpyKakaoClient implements KakaoClient {
    public KakaoTokenRequest getToken_argumentRequest;
    public KakaoTokenResponse getToken_returnValue = new KakaoTokenResponse(null, null, null, 0, null);
    public String getProfile_argumentAuthorization;

    @Override
    public KakaoTokenResponse getToken(KakaoTokenRequest request) {
        getToken_argumentRequest = request;
        return getToken_returnValue;
    }

    @Override
    public KakaoProfileResponse getProfile(String authorization) {
        getProfile_argumentAuthorization = authorization;
        return null;
    }
}
