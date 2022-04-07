package depromeet.ohgzoo.iam.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KakaoServiceImplTest {

    private KakaoService kakaoService;
    private SpyKakaoClient spyKakaoClient;

    @BeforeEach
    void setUp() {
        spyKakaoClient = new SpyKakaoClient();
        kakaoService = new KakaoServiceImpl(spyKakaoClient);
    }

    @Test
    void getLoginUrl_returnsOAuth2LoginUrl() {
        OAuth2LoginUrl result = kakaoService.getLoginUrl();

        String expected = new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                .append("?client_id=null")
                .append("&response_type=code")
                .append("&redirect_uri=null")
                .toString();

        assertThat(result.getLoginUrl()).isEqualTo(expected);
    }

    @Test
    void getToken_passesCodeToClient() {
        kakaoService.getToken("givenCode");

        assertThat(spyKakaoClient.getToken_argumentCode).isEqualTo("givenCode");
    }
}