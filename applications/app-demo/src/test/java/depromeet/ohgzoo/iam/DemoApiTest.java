package depromeet.ohgzoo.iam;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class DemoApiTest extends IntegrationTest {

    @Test
    void getDemo() throws Exception {
        mockMvc.perform(get("/"));
    }
}