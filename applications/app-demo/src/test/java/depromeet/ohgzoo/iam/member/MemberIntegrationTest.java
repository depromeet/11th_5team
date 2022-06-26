package depromeet.ohgzoo.iam.member;


import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@ExtendWith(MockitoExtension.class)
public class MemberIntegrationTest extends IntegrationTest {

    @MockBean
    MemberService memberService;

    @Test
    void getMySelf() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    void updateMember() throws Exception {
        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new Name\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(memberService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{memberId}", "1"))
                .andExpect(status().isOk());
    }
}
