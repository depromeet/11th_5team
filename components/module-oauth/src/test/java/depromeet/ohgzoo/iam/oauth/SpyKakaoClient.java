package depromeet.ohgzoo.iam.oauth;

public class SpyKakaoClient implements KakaoClient {
    public String getToken_argumentCode;

    @Override
    public void getToken(String code) {
        getToken_argumentCode = code;
    }
}
