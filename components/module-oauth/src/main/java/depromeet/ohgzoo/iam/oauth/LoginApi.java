package depromeet.ohgzoo.iam.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static depromeet.ohgzoo.iam.jwt.TokenName.REFRESH_TOKEN;

@RequiredArgsConstructor
@RestController
public class LoginApi {
    private final OauthServiceFactory oauthServiceFactory;

    @GetMapping("/refresh")
    public AuthToken refreshToken(HttpServletRequest request) {
        String refreshHeader = request.getHeader(REFRESH_TOKEN);

        final OauthService oauthService = oauthServiceFactory.getOauthService("kakao");
        return oauthService.getRefreshToken(refreshHeader);
    }

    @GetMapping("/signIn")
    public AuthToken signIn(@RequestParam String code) {
        final OauthService oauthService = oauthServiceFactory.getOauthService("kakao");
        return oauthService.getToken(code);
    }

    @GetMapping("/signIn/{provider}")
    public AuthToken signIn(@PathVariable String provider, @RequestParam String code) {
        final OauthService oauthService = oauthServiceFactory.getOauthService(provider);
        return oauthService.getToken(code);
    }
}
