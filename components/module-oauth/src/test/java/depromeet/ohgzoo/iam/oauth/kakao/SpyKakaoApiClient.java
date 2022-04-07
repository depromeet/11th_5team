package depromeet.ohgzoo.iam.oauth.kakao;

public class SpyKakaoApiClient implements KakaoApiClient {
    public String getProfile_argumentAuthorization;
    public String getProfile_returnValue;

    @Override
    public String getProfile(String authorization) {
        getProfile_argumentAuthorization = authorization;
        return getProfile_returnValue;
    }
}
