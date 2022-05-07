package depromeet.ohgzoo.iam.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static depromeet.ohgzoo.iam.jwt.TokenName.AUTH_TOKEN;
import static depromeet.ohgzoo.iam.jwt.TokenName.REFRESH_TOKEN;

@RequiredArgsConstructor
@Controller
public class RedirectController {
    private final OauthService oauthService;

    @ResponseStatus(HttpStatus.SEE_OTHER)
    @GetMapping("/login/oauth2/code/kakao")
    public void redirectCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        AuthToken token = oauthService.getToken(code);

        response.addCookie(getCookie(AUTH_TOKEN, token.getAuth()));
        response.addCookie(getCookie(REFRESH_TOKEN, token.getRefresh()));

        response.setHeader(AUTH_TOKEN, token.getAuth());
        response.setHeader(REFRESH_TOKEN, token.getRefresh());

        response.sendRedirect("http://localhost:3000/login/success");
    }

    private Cookie getCookie(String key, String auth) {
        Cookie cookie = new Cookie(key, auth);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        return cookie;
    }
}
