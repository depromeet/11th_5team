package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse.KakaoAccount;

public class SpyKakaoClient implements KakaoClient {
    public KakaoTokenRequest getToken_argumentRequest;
    public KakaoTokenResponse getToken_returnValue = new KakaoTokenResponse(null, null, null, 0, null);
    public String getProfile_argumentAuthorization;
    public KakaoProfileResponse getProfile_returnValue = new KakaoProfileResponse(null, new KakaoAccount(null, null));

    @Override
    public KakaoTokenResponse getToken(KakaoTokenRequest request) {
        getToken_argumentRequest = request;
        return getToken_returnValue;
    }

    @Override
    public KakaoProfileResponse getProfile(String authorization) {
        getProfile_argumentAuthorization = authorization;
        return getProfile_returnValue;
    }
}
