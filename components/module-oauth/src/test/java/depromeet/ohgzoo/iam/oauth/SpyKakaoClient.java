package depromeet.ohgzoo.iam.oauth;

public class SpyKakaoClient implements KakaoClient {
    public String getToken_argumentCode;

    @Override
    public String getToken(KakaoTokenRequest request) {
        getToken_argumentCode = request.getCode();
        return null;
    }
}
