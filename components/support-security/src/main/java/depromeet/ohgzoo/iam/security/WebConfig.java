package depromeet.ohgzoo.iam.security;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        whiteList.addAll(List.of("/css/**", "/*.ico", "/error", "/refresh", "/docs/**", "/signIn/**",
                "/api/v1/posts/all",
                "/api/v1/search/**",
                "/api/v1/sharing/**"));


        registry.addInterceptor(new SecurityInterceptor(jwtService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(whiteList);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(jwtService));
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://11th-5team-fe.vercel.app", "http://192.168.35.245:3000", "https://moodpic.kr", "https://www.moodpic.kr")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/")
                .setCachePeriod(20);
    }
}