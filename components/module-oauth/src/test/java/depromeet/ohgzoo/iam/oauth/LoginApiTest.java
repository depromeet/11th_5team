package depromeet.ohgzoo.iam.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static depromeet.ohgzoo.iam.jwt.TokenName.REFRESH_TOKEN;
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
    void refreshToken_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk());
    }

    @Test
    void refreshToken_passesRefreshHeaderToService() throws Exception {
        mockMvc.perform(get("/refresh")
                .header(REFRESH_TOKEN, "givenRefresh"));

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