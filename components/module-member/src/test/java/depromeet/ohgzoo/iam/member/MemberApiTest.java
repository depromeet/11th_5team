package depromeet.ohgzoo.iam.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiTest {

    private MockMvc mockMvc;
    private SpyMemberService spyMemberService;

    @BeforeEach
    void setUp() {
        spyMemberService = new SpyMemberService();

        mockMvc = MockMvcBuilders.standaloneSetup(new MemberApi(spyMemberService))
                .build();
    }

    @Test
    void getMySelf_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/users/me").header("auth", ""))
                .andExpect(status().isOk());
    }

    @Test
    void getMySelf_passesAuthHeaderToService() throws Exception {
        mockMvc.perform(get("/users/me")
                        .header("auth", "givenAuth"));

        assertThat(spyMemberService.getMySelf_argumentToken).isEqualTo("givenAuth");
    }

    @Test
    void getMySelf_returnsMemberResponse() throws Exception {
        spyMemberService.getMySelf_returnValue = new MemberResponse("givenProfileImg", "givenNickName");

        mockMvc.perform(get("/users/me").header("auth", ""))
                .andExpect(jsonPath("$.profileImg", equalTo("givenProfileImg")))
                .andExpect(jsonPath("$.nickname", equalTo("givenNickName")))
        ;
    }
}