package depromeet.ohgzoo.iam.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PostsApiIntegrationTest extends IntegrationTest {

    @Autowired
    private PostsRepository postsRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        postsRepository.deleteAll();
        postsRepository.save(new Posts(1L, FirstCategory.NO1, SecondCategory.Idk,
                "content", List.of("tag1", "tag2"), false));
        postsRepository.save(new Posts(1L, FirstCategory.NO1, SecondCategory.Unwritten,
                "content", List.of("tag1", "tag2"), false));
    }

    @Test
    void createPosts() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"NO1\",\"secondCategory\":\"Unwritten\",\"content\":\"content\",\"tags\":[\"1\",\"2\"],\"disclosure\":false}"))
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

    @Test
    void getRecentlyUnwrittenPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts/temp"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePosts() throws Exception {
        Posts posts = Posts.builder().content("test").build();
        postsRepository.save(posts);

        UpdatePostsRequest request = UpdatePostsRequest.builder().secondCategory(SecondCategory.NO1)
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
