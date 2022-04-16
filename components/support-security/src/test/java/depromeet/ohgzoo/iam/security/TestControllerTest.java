package depromeet.ohgzoo.iam.security;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import({AccessCheckAdvice.class, AopAutoConfiguration.class})
class TestControllerTest {

    @MockBean
    PostRepository postRepository;

    @MockBean
    JwtService jwtService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("test_AOP통과")
    public void test_AOP통과() throws Exception {
        doReturn(true).when(jwtService).verifyToken(anyString());
        doReturn("1").when(jwtService).getSubject(anyString());
        doReturn(Optional.of(new Post(1L, new Member(1L, null, "nickname", "token"))))
                .when(postRepository).findById(anyLong());

        mockMvc.perform(get("/test/{postid}", "1").header("AUTH_TOKEN", "token"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("test_AOP미통과")
    public void test_AOP미통과() throws Exception {
        doReturn(true).when(jwtService).verifyToken(anyString());
        doReturn("1").when(jwtService).getSubject(anyString());
        doReturn(Optional.of(new Post(1L, new Member(2L, null, "nickname", "token"))))
                .when(postRepository).findById(anyLong());

        mockMvc.perform(get("/test/{postid}", "1").header("AUTH_TOKEN", "token"))
                .andExpect(status().isOk());
    }
}