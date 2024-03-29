package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoOauthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(MockitoExtension.class)
public class LoginApiIntegrationTest extends IntegrationTest {

    @MockBean
    private KakaoOauthService oauthService;

    @MockBean
    private OauthServiceFactory serviceFactory;

    @BeforeEach
    void setUp() {
        given(oauthService.getToken(any())).willReturn(new AuthToken("auth token", "refresh token"));
        given(oauthService.getRefreshToken(any())).willReturn(new AuthToken("auth token", "refresh token"));

        given(serviceFactory.getOauthService(any())).willReturn(oauthService);
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/signIn")
                        .param("code", "kakaoCode"))
                .andExpect(status().isOk());
    }

    @Test
    void refresh() throws Exception {
        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk());
    }
}
