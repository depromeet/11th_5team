package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class SearchIntegrationTest extends IntegrationTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void search() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                        .param("keyword", "content"))
                .andExpect(status().isOk());
    }
}
