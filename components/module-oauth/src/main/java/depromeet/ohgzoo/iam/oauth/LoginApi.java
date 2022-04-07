package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.oauth.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginApi {
    private final KakaoService kakaoService;

    @GetMapping("/oauth2/authorization/kakao")
    public OAuth2LoginUrl login() {
        return kakaoService.getLoginUrl();
    }

    @GetMapping("/login/oauth2/code/kakao")
    public AuthToken redirectCallback(@RequestParam String code) {
        return kakaoService.getToken(code);
    }
}
