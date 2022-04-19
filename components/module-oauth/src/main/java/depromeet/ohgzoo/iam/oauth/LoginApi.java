package depromeet.ohgzoo.iam.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static depromeet.ohgzoo.iam.jwt.TokenName.REFRESH_TOKEN;

@RequiredArgsConstructor
@RestController
public class LoginApi {
    private final OauthService oauthService;

    @GetMapping("/oauth2/authorization/kakao")
    public Oauth2LoginUrl login() {
        return oauthService.getLoginUrl();
    }

    @GetMapping("/refresh")
    public AuthToken refreshToken(HttpServletRequest request) {
        String refreshHeader = request.getHeader(REFRESH_TOKEN);

        return oauthService.getRefreshToken(refreshHeader);
    }
}
