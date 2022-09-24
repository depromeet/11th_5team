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

        mockMvc = MockMvcBuilders.standaloneSetup(new LoginApi(new OauthServiceFactory(spyOauthService)))
                .build();
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

    @Test
    void signIn_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/signIn")
                        .param("code", ""))
                .andExpect(status().isOk());
    }

    @Test
    void signIn_passesCodeToService() throws Exception {
        mockMvc.perform(get("/signIn")
                        .param("code", "givenCode"));

        assertThat(spyOauthService.getToken_argumentCode).isEqualTo("givenCode");
    }

    @Test
    void signIn_returnsAuthToken() throws Exception {
        spyOauthService.getToken_returnValue = new AuthToken(
                "auth", "refresh"
        );

        mockMvc.perform(get("/signIn")
                        .param("code", ""))
                .andExpect(jsonPath("$.auth", equalTo("auth")))
                .andExpect(jsonPath("$.refresh", equalTo("refresh")));
    }
}