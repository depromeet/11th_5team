package depromeet.ohgzoo.iam.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PostsApiIntegrationTest extends IntegrationTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PostsRepository postsRepository;

    @Test
    void createPosts() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"geon\",\"firstCategory\":\"NO1\",\"secondCategory\":\"NO2\",\"content\":\"content\",\"tags\":[\"1\",\"2\"],\"disclosure\":false}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePosts() throws Exception {
        Posts posts = Posts.builder().content("test").build();
        postsRepository.save(posts);

        UpdatePostsRequest request = UpdatePostsRequest.builder().secondCategory(PostsSecondCategory.NO1)
                .content("content").tags(List.of("tag1", "tag2")).disclosure(false).build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/api/v1/posts/" + posts.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deletePosts() throws Exception {
        mockMvc.perform(delete("/api/v1/posts")
                        .param("postIds", "1, 2, 3"))
                .andExpect(status().isOk());
    }
}
