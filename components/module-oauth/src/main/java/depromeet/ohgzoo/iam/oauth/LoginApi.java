package depromeet.ohgzoo.iam.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginApi {
    private final OauthService OAuthService;

    @GetMapping("/oauth2/authorization/kakao")
    public Oauth2LoginUrl login() {
        return OAuthService.getLoginUrl();
    }

    @GetMapping("/login/oauth2/code/kakao")
    public AuthToken redirectCallback(@RequestParam String code) {
        return OAuthService.getToken(code);
    }
}
