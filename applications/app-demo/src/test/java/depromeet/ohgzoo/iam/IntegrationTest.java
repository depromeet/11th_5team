package depromeet.ohgzoo.iam;

import depromeet.ohgzoo.iam.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static depromeet.ohgzoo.iam.jwt.TokenName.AUTH_TOKEN;
import static depromeet.ohgzoo.iam.jwt.TokenName.REFRESH_TOKEN;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@TestPropertySource(properties = {
        "kakaoClientId=[kakao rest id]",
        "kakaoRedirectUrl=[kakao redirect url]"
})
public class IntegrationTest {

    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation, WebApplicationContext context) {
        document = MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document)
                .addFilter(new TestSupportFilter(jwtService))
                .build();
    }

    static class TestSupportFilter implements Filter {
        private JwtService jwtService;

        public TestSupportFilter(JwtService jwtService) {
            this.jwtService = jwtService;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            chain.doFilter(new TestRequestWrapper(httpServletRequest, jwtService), response);
        }
    }

    static class TestRequestWrapper extends HttpServletRequestWrapper {
        private String token;

        public TestRequestWrapper(HttpServletRequest request, JwtService jwtService) {
            super(request);
            this.token = jwtService.issuedToken("1", "USER", 3600);
        }

        public Enumeration<String> getHeaders(String name) {
            if (AUTH_TOKEN.equals(name) || REFRESH_TOKEN.equals(name)) {
                return Collections.enumeration(
                        Collections.singleton(token));
            }

            return super.getHeaders(name);
        }

        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            names.add(AUTH_TOKEN);
            names.add(REFRESH_TOKEN);
            return Collections.enumeration(names);
        }

        public String getHeader(String name) {
            if (AUTH_TOKEN.equals(name) || REFRESH_TOKEN.equals(name)) {
                return token;
            }

            return ((HttpServletRequest) getRequest()).getHeader(name);
        }
    }
}
