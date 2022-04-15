package depromeet.ohgzoo.iam.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LoginApi {
    private final OauthService oauthService;

    @GetMapping("/oauth2/authorization/kakao")
    public Oauth2LoginUrl login() {
        return oauthService.getLoginUrl();
    }

    @GetMapping("/login/oauth2/code/kakao")
    public AuthToken redirectCallback(@RequestParam String code) {
        return oauthService.getToken(code);
    }

    @GetMapping("/refresh")
    public AuthToken refreshToken(HttpServletRequest request) {
        String refreshHeader = request.getHeader("REFRESH");

        return oauthService.getRefreshToken(refreshHeader);
    }
}
