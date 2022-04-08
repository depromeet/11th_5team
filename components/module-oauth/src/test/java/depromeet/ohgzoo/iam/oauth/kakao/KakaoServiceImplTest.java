package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.oauth.OAuth2LoginUrl;
import depromeet.ohgzoo.iam.oauth.OAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KakaoServiceImplTest {

    private OAuthService OAuthService;
    private SpyKakaoClient spyKakaoClient;

    @BeforeEach
    void setUp() {
        spyKakaoClient = new SpyKakaoClient();
        OAuthService = new KakaoServiceImpl(spyKakaoClient);
    }

    @Test
    void getLoginUrl_returnsOAuth2LoginUrl() {
        OAuth2LoginUrl result = OAuthService.getLoginUrl();

        String expected = new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                .append("?client_id=null")
                .append("&response_type=code")
                .append("&redirect_uri=null")
                .toString();

        assertThat(result.getLoginUrl()).isEqualTo(expected);
    }

    @Test
    void getToken_passesCodeToClient() {
        OAuthService.getToken("givenCode");

        assertThat(spyKakaoClient.getToken_argumentRequest.getCode()).isEqualTo("givenCode");
    }

    @Test
    void getToken_passesKakaoTokenResponseToClient() {
        String givenAccessToken = "accessToken";
        spyKakaoClient.getToken_returnValue = new KakaoTokenResponse(givenAccessToken, null, null, 0, null);

        OAuthService.getToken(null);

        assertThat(spyKakaoClient.getProfile_argumentAuthorization).isEqualTo("Bearer " + givenAccessToken);
    }
}