package depromeet.ohgzoo.iam.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginApiTest {

    private MockMvc mockMvc;
    private SpyOauthService spyOauthService;

    @BeforeEach
    void setUp() {
        spyOauthService = new SpyOauthService();
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginApi(spyOauthService))
                .build();
    }

    @Test
    void login_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/kakao"))
                .andExpect(status().isOk());
    }

    @Test
    void login_returnsLoginUrl() throws Exception {
        spyOauthService.getLoginUrl_returnValue = new Oauth2LoginUrl("loginUrl");

        mockMvc.perform(get("/oauth2/authorization/kakao"))
                .andExpect(jsonPath("$.loginUrl", equalTo("loginUrl")));
    }

    @Test
    void redirectCallback_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/login/oauth2/code/kakao").param("code", ""))
                .andExpect(status().isOk());
    }

    @Test
    void redirectCallback_passesCodeToService() throws Exception {
        mockMvc.perform(get("/login/oauth2/code/kakao").param("code", "givenCode"));

        assertThat(spyOauthService.getToken_argumentCode).isEqualTo("givenCode");
    }

    @Test
    void redirectCallback_returnsAuthToken() throws Exception {
        spyOauthService.getToken_returnValue = new AuthToken("givenAuthToken", "givenRefreshToken");

        mockMvc.perform(get("/login/oauth2/code/kakao").param("code", ""))
                .andExpect(jsonPath("$.auth", equalTo("givenAuthToken")))
                .andExpect(jsonPath("$.refresh", equalTo("givenRefreshToken")))
        ;
    }

    @Test
    void refreshToken_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk());
    }

    @Test
    void refreshToken_passesRefreshHeaderToService() throws Exception {
        mockMvc.perform(get("/refresh")
                .header("REFRESH", "givenRefresh"));

        assertThat(spyOauthService.getRefreshToken_argumentRefreshToken).isEqualTo("givenRefresh");
    }

    @Test
    void refreshToken_returnsAuthToken() throws Exception {
        spyOauthService.getRefreshToken_returnValue = new AuthToken(
                "auth", "refresh"
        );

        mockMvc.perform(get("/refresh"))
                .andExpect(jsonPath("$.auth", equalTo("auth")))
                .andExpect(jsonPath("$.refresh", equalTo("refresh")));
    }
}