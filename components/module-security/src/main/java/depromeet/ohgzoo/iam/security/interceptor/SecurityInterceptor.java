package depromeet.ohgzoo.iam.security.interceptor;

import depromeet.ohgzoo.iam.jwt.JwtService;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {

    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private final JwtService jwtService;

    public SecurityInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTH_TOKEN);

        if (StringUtils.hasText(token) && jwtService.verifyToken(token)) {
            return true;
        } else {
            response.sendRedirect("/refresh?redirectURL=" + request.getRequestURI());
            return false;
        }
    }
}