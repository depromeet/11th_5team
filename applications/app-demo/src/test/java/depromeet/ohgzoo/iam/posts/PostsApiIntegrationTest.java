package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostsApiIntegrationTest extends IntegrationTest {

    @Autowired
    private PostsRepository postsRepository;

    @BeforeEach
    void setUp() {
        postsRepository.deleteAll();
        postsRepository.save(new Posts(1L, PostsFirstCategory.NO1, PostsSecondCategory.NO2,
                "content", List.of("tag1", "tag2"), false));
    }

    @Test
    void createPosts() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"geon\",\"firstCategory\":\"NO1\",\"secondCategory\":\"NO2\",\"content\":\"content\",\"tags\":[\"1\",\"2\"],\"disclosure\":false}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getMyPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsByTag() throws Exception {
        mockMvc.perform(get("/api/v1/posts/search")
                        .param("tag", "tag1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsOrderByPopular() throws Exception {
        mockMvc.perform(get("/api/v1/posts/popular"))
                .andExpect(status().isOk());
    }
}
