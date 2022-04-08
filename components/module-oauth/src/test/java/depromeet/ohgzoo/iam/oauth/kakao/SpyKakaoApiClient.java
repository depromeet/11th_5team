package depromeet.ohgzoo.iam.oauth.kakao;

public class SpyKakaoApiClient implements KakaoApiClient {
    public String getProfile_argumentAuthorization;
    public KakaoProfileResponse getProfile_returnValue;

    @Override
    public KakaoProfileResponse getProfile(String authorization) {
        getProfile_argumentAuthorization = authorization;
        return getProfile_returnValue;
    }
}
