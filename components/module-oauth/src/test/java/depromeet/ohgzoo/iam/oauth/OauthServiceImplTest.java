package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.jwt.SpyJwtService;
import depromeet.ohgzoo.iam.jwt.UnAuthenticationException;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse.KakaoAccount;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenResponse;
import depromeet.ohgzoo.iam.oauth.kakao.SpyKakaoClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OauthServiceImplTest {

    private OauthService oauthService;
    private SpyKakaoClient spyKakaoClient;
    private SpyJwtService spyJwtService;

    @BeforeEach
    void setUp() {
        spyKakaoClient = new SpyKakaoClient();
        spyJwtService = new SpyJwtService();
        oauthService = new OauthServiceImpl(spyKakaoClient, spyJwtService);
    }

    @Test
    void getLoginUrl_returnsOAuth2LoginUrl() {
        Oauth2LoginUrl result = oauthService.getLoginUrl();

        String expected = new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                .append("?client_id=null")
                .append("&response_type=code")
                .append("&redirect_uri=null")
                .toString();

        assertThat(result.getLoginUrl()).isEqualTo(expected);
    }

    @Test
    void getToken_passesCodeToClient() {
        oauthService.getToken("givenCode");

        assertThat(spyKakaoClient.getToken_argumentRequest.getCode()).isEqualTo("givenCode");
    }

    @Test
    void getToken_passesKakaoTokenResponseToClient() {
        String givenAccessToken = "accessToken";
        spyKakaoClient.getToken_returnValue = new KakaoTokenResponse(givenAccessToken, null, null, 0, null);

        oauthService.getToken(null);

        assertThat(spyKakaoClient.getProfile_argumentAuthorization).isEqualTo("Bearer " + givenAccessToken);
    }

    @Test
    void getToken_passesProfileEmailToJwtService_forAuthAndRefresh() {
        spyKakaoClient.getProfile_returnValue = new KakaoProfileResponse(null, new KakaoAccount("givenEmail", null));

        oauthService.getToken(null);

        assertThat(spyJwtService.issuedToken_callCount).isEqualTo(2);

        assertThat(spyJwtService.getIssuedToken_argumentSubject()).isEqualTo("givenEmail");
        assertThat(spyJwtService.getIssuedToken_argumentRole()).isEqualTo("USER");
        assertThat(spyJwtService.getIssuedToken_argumentPeriod()).isEqualTo(3600);

        assertThat(spyJwtService.getIssuedToken_argumentSubject()).isEqualTo("givenEmail");
        assertThat(spyJwtService.getIssuedToken_argumentRole()).isEqualTo("USER");
        assertThat(spyJwtService.getIssuedToken_argumentPeriod()).isEqualTo(36000);
    }


    @Test
    void getRefreshToken_callsVerifyTokenInJwtService() {
        oauthService.getRefreshToken("givenToken");

        assertThat(spyJwtService.verifyToken_argumentToken).isEqualTo("givenToken");
    }

    @Test
    void getRefreshToken_callsGetSubjectInJwtService_whenValidToken() {
        spyJwtService.verifyToken_returnValue = true;

        oauthService.getRefreshToken("givenToken");

        assertThat(spyJwtService.getSubject_argumentToken).isEqualTo("givenToken");
    }

    @Test
    void getRefreshToken_throwsUnAuthenticationException_whenNotValidToken() {
        spyJwtService.verifyToken_returnValue = false;

        Assertions.assertThatThrownBy(() -> oauthService.getRefreshToken(""))
                .isInstanceOf(UnAuthenticationException.class)
                .hasMessage("토큰이 만료되었습니다.");
        ;
    }

    @Test
    void getRefreshToken_passesSubjectToJwtService() {
        spyJwtService.verifyToken_returnValue = true;
        spyJwtService.getSubject_returnValue = "givenSubject";

        oauthService.getRefreshToken("givenToken");

        assertThat(spyJwtService.issuedToken_callCount).isEqualTo(2);
        assertThat(spyJwtService.getIssuedToken_argumentSubject()).isEqualTo("givenSubject");
        assertThat(spyJwtService.getIssuedToken_argumentRole()).isEqualTo("USER");
        assertThat(spyJwtService.getIssuedToken_argumentPeriod()).isEqualTo(3600);

        assertThat(spyJwtService.getIssuedToken_argumentSubject()).isEqualTo("givenSubject");
        assertThat(spyJwtService.getIssuedToken_argumentRole()).isEqualTo("USER");
        assertThat(spyJwtService.getIssuedToken_argumentPeriod()).isEqualTo(36000);
    }

    @Test
    void getRefreshToken_returnsAuthToken() {
        spyJwtService.verifyToken_returnValue = true;
        spyJwtService.getIssuedToken_returnValue = "newToken";

        AuthToken result = oauthService.getRefreshToken("");

        assertThat(result.getAuth()).isEqualTo("newToken");
        assertThat(result.getRefresh()).isEqualTo("newToken");
    }
}