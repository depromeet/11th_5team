package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostsApiIntegrationTest extends IntegrationTest {
    @Test
    void createPosts() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"geon\",\"firstCategory\":\"NO1\",\"secondCategory\":\"NO2\",\"content\":\"content\",\"tags\":[\"1\",\"2\"],\"disclosure\":false}"))
                .andExpect(status().isCreated());
    }
}
