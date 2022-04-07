package depromeet.ohgzoo.iam.oauth;

import lombok.Getter;

@Getter
public class KakaoParam {
    private final String kakaoClientId;
    private final String kakaoBaseUrl;
    private final String kakaoRedirect;

    public KakaoParam(String kakaoClientId, String kakaoBaseUrl, String kakaoRedirect) {
        this.kakaoClientId = kakaoClientId;
        this.kakaoBaseUrl = kakaoBaseUrl;
        this.kakaoRedirect = kakaoRedirect;
    }
}
