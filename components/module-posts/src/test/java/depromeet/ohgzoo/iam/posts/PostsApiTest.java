package depromeet.ohgzoo.iam.posts;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostsApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    private SpyPostsService spyPostsService;

    @BeforeEach
    void setUp() {
        spyPostsService = new SpyPostsService();
        mockMvc = MockMvcBuilders.standaloneSetup(new PostsApi(spyPostsService)).build();
    }

    @Test
    void createPosts_returnsCreatedHttpStatus() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createPosts_returnsPostsId() throws Exception {
        spyPostsService.createPosts_returnValue = new CreatePostsResult(1L);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(jsonPath("$.postId", equalTo(1)));
    }

    @Test
    void updatePosts_status() throws Exception {
        UpdatePostsRequest request = UpdatePostsRequest.builder().secondCategory(PostsSecondCategory.NO1)
                .content("test").tags(List.of("tag")).disclosure(false).build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/api/v1/posts/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deletePosts_status() throws Exception {
        mockMvc.perform(delete("/api/v1/posts").param("postIds", "1, 2, 3"))
                .andExpect(status().isOk());
    }

    @Test
    void createPosts_passesCreateRequestToService() throws Exception {
        CreatePostsRequest givenRequest = new CreatePostsRequest(
                PostsFirstCategory.NO1,
                PostsSecondCategory.NO2,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true
        );

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andDo(print());

        assertThat(spyPostsService.createPosts_argumentRequest.getFirstCategory()).isEqualTo(PostsFirstCategory.NO1);
        assertThat(spyPostsService.createPosts_argumentRequest.getSecondCategory()).isEqualTo(PostsSecondCategory.NO2);
        assertThat(spyPostsService.createPosts_argumentRequest.getContent()).isEqualTo("givenContent");
        assertThat(spyPostsService.createPosts_argumentRequest.getTags()).containsExactly("tag1", "tag2", "tag3");
        assertThat(spyPostsService.createPosts_argumentRequest.isDisclosure()).isTrue();
    }
}