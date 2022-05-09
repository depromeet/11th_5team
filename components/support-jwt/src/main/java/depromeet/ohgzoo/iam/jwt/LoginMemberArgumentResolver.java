package depromeet.ohgzoo.iam.jwt;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    public LoginMemberArgumentResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        try {
            return Long.valueOf(getMemberId(request));
        } catch (RuntimeException e) {
            return null;
        }
    }

    private String getMemberId(HttpServletRequest request) {
        return jwtService.getSubject(getAuthToken(request));
    }

    private String getAuthToken(HttpServletRequest request) {
        return request.getHeader(TokenName.AUTH_TOKEN);
    }
}