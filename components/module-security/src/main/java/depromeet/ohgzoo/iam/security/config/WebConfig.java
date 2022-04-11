package depromeet.ohgzoo.iam.security.config;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.security.interceptor.SecurityInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtService jwtService;
    private List<String> whiteList = new ArrayList<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        whiteList.addAll(List.of("/css/**", "/*.ico", "/error", "/refresh", "/docs/**", "/oauth2/**", "/login/**"));

        registry.addInterceptor(new SecurityInterceptor(jwtService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(whiteList);
    }
}