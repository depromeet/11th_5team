package depromeet.ohgzoo.iam.oauth.kakao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KakaoClientAdapterTest {

    private KakaoClient kakaoClient;
    private SpyKakaoAuthClient spyKakaoAuthClient;
    private SpyKakaoApiClient spyKakaoApiClient;

    @BeforeEach
    void setUp() {
        spyKakaoAuthClient = new SpyKakaoAuthClient();
        spyKakaoApiClient = new SpyKakaoApiClient();

        kakaoClient = new KakaoClientAdapter(spyKakaoAuthClient, spyKakaoApiClient);
    }

    @Test
    void getToken_passesRequestToAuthClient() {
        KakaoTokenRequest givenRequest = new KakaoTokenRequest(null, null, null);

        kakaoClient.getToken(givenRequest);

        assertThat(spyKakaoAuthClient.getToken_argumentCode).isEqualTo(givenRequest);
    }

    @Test
    void getToken_returnsKakaoTokenReponse() {
        KakaoTokenResponse givenTokenResponse = new KakaoTokenResponse(null, null, null, 0, null);
        spyKakaoAuthClient.getToken_returnValue = givenTokenResponse;

        KakaoTokenResponse result = kakaoClient.getToken(null);

        assertThat(result).isEqualTo(givenTokenResponse);
    }

    @Test
    void getProfile_passesAuthorizationToApiClient() {
        String givenAuthorization = "givenAuthorization";

        kakaoClient.getProfile(givenAuthorization);

        assertThat(spyKakaoApiClient.getProfile_argumentAuthorization).isEqualTo(givenAuthorization);
    }

    @Test
    void getProfile_returns() {
        String expectedProfile = "profile";
        spyKakaoApiClient.getProfile_returnValue = expectedProfile;

        String result = kakaoClient.getProfile(null);

        assertThat(result).isEqualTo(expectedProfile);
    }
}