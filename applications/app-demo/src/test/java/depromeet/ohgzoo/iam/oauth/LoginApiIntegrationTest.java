package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginApiIntegrationTest extends IntegrationTest {

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/kakao"))
                .andExpect(status().isOk());
    }
}
