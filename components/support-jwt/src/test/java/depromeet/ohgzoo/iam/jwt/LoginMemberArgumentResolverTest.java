package depromeet.ohgzoo.iam.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginMemberArgumentResolverTest {

    JwtService jwtService = new JwtServiceImpl();
    LoginMemberArgumentResolver loginMemberArgumentResolver = new LoginMemberArgumentResolver(jwtService);
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setCustomArgumentResolvers(loginMemberArgumentResolver).build();
    }

    @Test
    @DisplayName("supportsParameter_withAnnotationAndLongType")
    public void supportsParameter_withAnnotationAndLongType() throws Exception {
        Class<TestController> testControllerClass = TestController.class;
        Method method = testControllerClass.getMethod("withAnnotationAndLongType", Long.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        assertThat(loginMemberArgumentResolver.supportsParameter(methodParameter)).isEqualTo(true);
    }

    @Test
    @DisplayName("supportsParameter_withAnnotationAndStringType")
    public void supportsParameter_withAnnotationAndStringType() throws Exception {
        Class<TestController> testControllerClass = TestController.class;
        Method method = testControllerClass.getMethod("withAnnotationAndStringType", String.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        assertThat(loginMemberArgumentResolver.supportsParameter(methodParameter)).isEqualTo(false);
    }

    @Test
    @DisplayName("supportsParameter_withLongType")
    public void supportsParameter_withLongType() throws Exception {
        Class<TestController> testControllerClass = TestController.class;
        Method method = testControllerClass.getMethod("withLongType", Long.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        assertThat(loginMemberArgumentResolver.supportsParameter(methodParameter)).isEqualTo(false);
    }

    @Test
    @DisplayName("supportsParameter_withoutBoth")
    public void supportsParameter_withoutBoth() throws Exception {
        Class<TestController> testControllerClass = TestController.class;
        Method method = testControllerClass.getMethod("withoutBoth");
        MethodParameter methodParameter = new MethodParameter(method, -1);

        assertThat(loginMemberArgumentResolver.supportsParameter(methodParameter)).isEqualTo(false);
    }

    @Test
    @DisplayName("resolveArgument_returnMemberId")
    public void resolveArgument_returnMemberId() throws Exception {
        Class<TestController> testControllerClass = TestController.class;
        Method method = testControllerClass.getMethod("withAnnotationAndLongType", Long.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        String authToken = jwtService.issuedToken("1", "ROLE_USER", 3600);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(TokenName.AUTH_TOKEN, authToken);
        NativeWebRequest nativeWebRequest = new DispatcherServletWebRequest(request);

        Long memberId = (Long) loginMemberArgumentResolver.resolveArgument(methodParameter, null,
                nativeWebRequest, null);

        assertThat(memberId).isEqualTo(1L);
    }

    @Test
    @DisplayName("withAnnotationAndLongType_status")
    public void withAnnotationAndLongType_status() throws Exception {
        String authToken = jwtService.issuedToken("1", "ROLE_USER", 3600);

        mockMvc.perform(get("/withAnnotationAndLongType").header(TokenName.AUTH_TOKEN, authToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("withAnnotationAndLongType_returnValue")
    public void withAnnotationAndLongType_returnValue() throws Exception {
        String authToken1 = jwtService.issuedToken("1", "ROLE_USER", 3600);
        mockMvc.perform(get("/withAnnotationAndLongType").header(TokenName.AUTH_TOKEN, authToken1))
                .andExpect(content().string("1"))
                .andDo(print());

        String authToken2 = jwtService.issuedToken("2", "ROLE_USER", 3600);
        mockMvc.perform(get("/withAnnotationAndLongType").header(TokenName.AUTH_TOKEN, authToken2))
                .andExpect(content().string("2"))
                .andDo(print());
    }

    @Test
    @DisplayName("withAnnotationAndStringType_returnValue")
    public void withAnnotationAndStringType_returnValue() throws Exception {
        String authToken = jwtService.issuedToken("1", "ROLE_USER", 3600);

        mockMvc.perform(get("/withAnnotationAndStringType").header(TokenName.AUTH_TOKEN, authToken))
                .andExpect(content().string(""))
                .andDo(print());
    }

    @RestController
    static class TestController {
        @GetMapping("/withAnnotationAndLongType")
        public Long withAnnotationAndLongType(@Login Long memberId) {
            return memberId;
        }

        @GetMapping("/withAnnotationAndStringType")
        public String withAnnotationAndStringType(@Login String memberId) {
            return memberId;
        }

        @GetMapping("/withLongType")
        public Long withLongType(Long memberId) {
            return memberId;
        }

        @GetMapping("/withoutBoth")
        public String withoutBoth() {
            return "ok";
        }
    }
}