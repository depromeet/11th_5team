package depromeet.ohgzoo.iam.oauth;

import feign.form.FormProperty;
import lombok.Getter;

@Getter
public class KakaoTokenRequest {
    @FormProperty("grant_type")
    private String grantType = "authorization_code";
    @FormProperty("client_id")
    private String clientId;
    @FormProperty("redirect_uri")
    private String redirectUri;
    @FormProperty("code")
    private String code;

    public KakaoTokenRequest(String clientId, String redirect_uri, String code) {
        this.clientId = clientId;
        this.redirectUri = redirect_uri;
        this.code = code;
    }
}
