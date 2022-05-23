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

import java.time.LocalDateTime;
import java.util.List;

import static depromeet.ohgzoo.iam.posts.PostFixtures.aPost;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PostsApiIntegrationTest extends IntegrationTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private PostsRepository postsRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createPosts() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"SADNESS\",\"secondCategory\":\"Unwritten\",\"content\":\"content\",\"tags\":[\"1\",\"2\"],\"disclosure\":false, \"folderId\": 1}"))
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
        Posts posts = aPost().content("test").memberId(1L).build();
        postsRepository.save(posts);

        UpdatePostsRequest request = UpdatePostsRequest.builder().secondCategory(SecondCategory.SADNESS)
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
                        .param("postIds", "1,2"))
                .andExpect(status().isOk());
    }

    @Test
    public void increaseViews() throws Exception {
        mockMvc.perform(patch("/api/v1/posts/{postid}/views", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsById() throws Exception {
        Posts posts = aPost().content("test").build();
        postsRepository.save(posts);
        mockMvc.perform(get("/api/v1/posts/" + posts.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategories() throws Exception {
        mockMvc.perform(get("/api/v1/posts/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryItems() throws Exception {
        postsRepository.save(Posts.builder().id("1").memberId(1L).firstCategory(FirstCategory.ANXIOUS)
                .secondCategory(SecondCategory.JOY).content("content").tags(List.of("tag")).disclosure(false).views(0).createdAt(LocalDateTime.now()).build());

        mockMvc.perform(get("/api/v1/posts/categories/1")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk());
    }
}
