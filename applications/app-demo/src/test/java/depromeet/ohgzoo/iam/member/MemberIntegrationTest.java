package depromeet.ohgzoo.iam.member;


import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MemberIntegrationTest extends IntegrationTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member(1L, "profileImg", "nickname", "identifyToken"));
    }

    @Test
    void getMySelf() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }
}
