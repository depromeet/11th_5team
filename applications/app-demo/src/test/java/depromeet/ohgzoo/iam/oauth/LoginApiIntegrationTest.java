package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoginApiIntegrationTest extends IntegrationTest {

    @MockBean
    private OauthService oauthService;

    @BeforeEach
    void setUp() {
        given(oauthService.getToken(any())).willReturn(new AuthToken("auth token", "refresh token"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/kakao"))
                .andExpect(status().isOk());
    }

    @Test
    void refresh() throws Exception {
        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk());
    }

    @Test
    void redirectCallback() throws Exception {
        mockMvc.perform(get("/login/oauth2/code/kakao")
                        .param("code", "token"))
                .andExpect(status().is3xxRedirection());
    }
}
