package depromeet.ohgzoo.iam.controller;

import depromeet.ohgzoo.iam.jwt.JwtServiceImpl;
import depromeet.ohgzoo.iam.security.config.WebConfig;
import depromeet.ohgzoo.iam.security.controller.TokenRefreshController;
import depromeet.ohgzoo.iam.security.dto.AuthToken;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;
import depromeet.ohgzoo.iam.security.service.TokenRefreshService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TokenRefreshController.class)
@Import(WebConfig.class)
class TokenRefreshControllerTest {

    @SpyBean
    JwtServiceImpl jwtService;

    @MockBean
    TokenRefreshService tokenRefreshService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("validToken")
    public void validToken() throws Exception {
        AuthToken authToken = new AuthToken(
                jwtService.issuedToken("auth", "USER", 100000),
                jwtService.issuedToken("refresh", "USER", 100000)
        );

        mockMvc.perform(get("/validtoken").header("AUTH_TOKEN", authToken.getAuth()))
                .andExpect(status().isOk())
                .andExpect(content().string("valid token"))
                .andDo(print());
    }

    @Test
    @DisplayName("expiredToken")
    public void expiredToken() throws Exception {
        AuthToken authToken = new AuthToken(
                jwtService.issuedToken("auth", "USER", 0),
                jwtService.issuedToken("refresh", "USER", 100000)
        );

        mockMvc.perform(get("/validtoken").header("AUTH_TOKEN", authToken.getAuth()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/refresh?redirectURL=/validtoken"))
                .andDo(print());
    }

    @Test
    @DisplayName("noToken")
    public void noToken() throws Exception {
        mockMvc.perform(get("/validtoken"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/refresh?redirectURL=/validtoken"))
                .andDo(print());
    }

    @Test
    @DisplayName("refresh_성공")
    public void refresh_성공() throws Exception {
        AuthToken authToken = new AuthToken("auth", "refresh");
        when(tokenRefreshService.createNewAuthToken(any())).thenReturn(authToken);

        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auth").value("auth"))
                .andExpect(jsonPath("$.refresh").value("refresh"))
                .andDo(print());
    }

    @Test
    @DisplayName("refresh_실패")
    public void refresh_실패() throws Exception {
        when(tokenRefreshService.createNewAuthToken(any())).thenThrow(new UnAuthenticationException("토큰이 만료되었습니다."));

        mockMvc.perform(get("/refresh"))
                .andExpect(jsonPath("$.msg").value("토큰이 만료되었습니다."))
                .andExpect(jsonPath("$.code").value("401"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}