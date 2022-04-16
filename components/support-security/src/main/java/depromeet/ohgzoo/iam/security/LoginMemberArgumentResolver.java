package depromeet.ohgzoo.iam.security;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.jwt.TokenName;
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
        boolean hasStringType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasStringType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return Long.valueOf(jwtService.getSubject(request.getHeader(TokenName.AUTH_TOKEN)));
    }
}
