package depromeet.ohgzoo.iam.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import depromeet.ohgzoo.iam.jwt.StubJwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostExtensionApiTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    private SpyPostExtensionService spyPostExtensionService;
    private StubJwtService stubJwtService;

    @BeforeEach
    void setUp() {
        stubJwtService = new StubJwtService();
        spyPostExtensionService = new SpyPostExtensionService();
        mockMvc = MockMvcBuilders.standaloneSetup(new PostExtensionApi(spyPostExtensionService))
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(stubJwtService))
                .build();
    }

    @Test
    void createPost_returnsCreatedHttpStatus() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createPost_passesCreateRequestToService() throws Exception {
        CreatePostRequest givenRequest = new CreatePostRequest(
                FirstCategory.SADNESS,
                SecondCategory.DONTKNOW,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true,
                1L
        );

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenRequest)));

        assertThat(spyPostExtensionService.createPost_argumentRequest.getFirstCategory()).isEqualTo(FirstCategory.SADNESS);
        assertThat(spyPostExtensionService.createPost_argumentRequest.getSecondCategory()).isEqualTo(SecondCategory.DONTKNOW);
        assertThat(spyPostExtensionService.createPost_argumentRequest.getContent()).isEqualTo("givenContent");
        assertThat(spyPostExtensionService.createPost_argumentRequest.getTags()).containsExactly("tag1", "tag2", "tag3");
        assertThat(spyPostExtensionService.createPost_argumentRequest.isDisclosure()).isTrue();
        assertThat(spyPostExtensionService.createPost_argumentRequest.getFolderId()).isEqualTo(1L);
    }

    @Test
    void createPost_passesMemberIdToService() throws Exception {
        stubJwtService.getSubject_returnValue = "1";

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("AUTH_TOKEN", "givenToken"));

        assertThat(spyPostExtensionService.createPost_argumentMemberId).isEqualTo(1L);
    }

    @Test
    void createPost_returnsPostId() throws Exception {
        spyPostExtensionService.createPost_returnValue = new CreatePostResult("1");

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(jsonPath("$.postId", equalTo("1")));
    }

    @Test
    void deletePosts_isOk() throws Exception {
        mockMvc.perform(delete("/api/v1/posts")
                        .param("postIds", "1, 2, 3"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePosts_passesMemberIdToService() throws Exception {
        stubJwtService.getSubject_returnValue = "1";

        mockMvc.perform(delete("/api/v1/posts")
                .param("postIds", "1,2,3")
                .header("AUTH_TOKEN", "givenToken"));

        assertThat(spyPostExtensionService.deletePosts_argumentMemberId).isEqualTo(1L);

    }

    @Test
    void deletePosts_passesPostIdsToService() throws Exception {
        stubJwtService.getSubject_returnValue = "1";

        mockMvc.perform(delete("/api/v1/posts")
                .param("postIds", "1,2,3")
                .header("AUTH_TOKEN", "givenToken"));

        assertThat(spyPostExtensionService.deletePosts_argumentPostsId).isEqualTo(List.of("1", "2", "3"));
    }

    @Test
    public void increaseViews_isOk() throws Exception {
        mockMvc.perform(patch("/api/v1/posts/1/views"))
                .andExpect(status().isOk());
    }

    @Test
    public void increaseViews_passesPostIdToService() throws Exception {
        mockMvc.perform(patch("/api/v1/posts/{postid}/views", "1"))
                .andExpect(status().isOk());

        assertThat(spyPostExtensionService.increaseViews_argumentPostId).isEqualTo("1");
    }
}