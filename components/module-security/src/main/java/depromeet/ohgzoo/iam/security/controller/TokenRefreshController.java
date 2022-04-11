package depromeet.ohgzoo.iam.security.controller;

import depromeet.ohgzoo.iam.security.dto.AuthToken;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;
import depromeet.ohgzoo.iam.security.service.TokenRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TokenRefreshController {

    private final TokenRefreshService tokenRefreshService;

    @GetMapping("/refresh")
    public AuthToken refreshToken(HttpServletRequest request) throws UnAuthenticationException {
        return tokenRefreshService.createNewAuthToken(request);
    }

    @GetMapping("/pathexcludetest1")
    public String pathExcludeTest1() {
        return "ok1";
    }

    @GetMapping("/pathexcludetest2")
    public String pathExcludeTest2() {
        return "ok2";
    }

    @GetMapping("/validtoken")
    public String validToken() {
        return "valid token";
    }

}