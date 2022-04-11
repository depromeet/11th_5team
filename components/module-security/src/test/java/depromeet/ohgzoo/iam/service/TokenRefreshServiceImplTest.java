package depromeet.ohgzoo.iam.service;

import depromeet.ohgzoo.iam.security.dto.AuthToken;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;
import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.jwt.JwtServiceImpl;
import depromeet.ohgzoo.iam.security.service.TokenRefreshServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TokenRefreshServiceImplTest {

    JwtService jwtService = new JwtServiceImpl();
    TokenRefreshServiceImpl tokenRefreshService = new TokenRefreshServiceImpl(jwtService);

    @Test
    @DisplayName("validRefreshToken")
    public void validRefreshToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthToken authToken = new AuthToken(
                jwtService.issuedToken("email", "USER", 0),
                jwtService.issuedToken("email", "USER", 1000000)
        );
        request.addHeader("REFRESH_TOKEN", authToken.getRefresh());
        AuthToken newAuthToken = tokenRefreshService.createNewAuthToken(request);

        assertThat(jwtService.getSubject(newAuthToken.getAuth())).isEqualTo("email");
        assertThat(jwtService.getSubject(newAuthToken.getRefresh())).isEqualTo("email");
    }

    @Test
    @DisplayName("expiredRefreshToken")
    public void expiredRefreshToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthToken authToken = new AuthToken(
                jwtService.issuedToken("email", "USER", 0),
                jwtService.issuedToken("email", "USER", 0)
        );
        request.addHeader("REFRESH_TOKEN", authToken.getRefresh());

        assertThatThrownBy(() -> tokenRefreshService.createNewAuthToken(request))
                .isInstanceOf(UnAuthenticationException.class);
    }

    @Test
    @DisplayName("noRefreshToken")
    public void noRefreshToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThatThrownBy(() -> tokenRefreshService.createNewAuthToken(request))
                .isInstanceOf(UnAuthenticationException.class);
    }
}