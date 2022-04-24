package depromeet.ohgzoo.iam.posts;


import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import depromeet.ohgzoo.iam.jwt.SpyJwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostsApiTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SpyPostsService spyPostsService;
    private SpyJwtService spyJwtService;

    @BeforeEach
    void setUp() {
        spyJwtService = new SpyJwtService();
        spyPostsService = new SpyPostsService();
        mockMvc = MockMvcBuilders.standaloneSetup(new PostsApi(spyPostsService))
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(spyJwtService))
                .build();
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

    @Test
    void getMyPosts_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void getMyPosts_returnsPostsDtoList() throws Exception {
        spyPostsService.getPostsByMemberId_returnValue = List.of(
                new PostsDto(
                        1L,
                        PostsFirstCategory.NO1,
                        PostsSecondCategory.NO1,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].firstCategory", equalTo("NO1")))
                .andExpect(jsonPath("$[0].secondCategory", equalTo("NO1")))
                .andExpect(jsonPath("$[0].content", equalTo("content")))
                .andExpect(jsonPath("$[0].tags", contains("1", "2")))
                .andExpect(jsonPath("$[0].disclosure", equalTo(true)))
                .andExpect(jsonPath("$[0].views", equalTo(1)))
                .andExpect(jsonPath("$[0].createdAt", equalTo("2022-04-24 12:30:30")))
        ;
    }

    @Test
    void getMyPosts_passesMemberIdAndPageSizeToService() throws Exception {
        spyJwtService.getSubject_returnValue = "1";

        mockMvc.perform(get("/api/v1/posts")
                .param("page", "1")
                .param("size", "1"));

        assertThat(spyPostsService.getPostsByMemberId_argumentMemberId).isEqualTo(1L);
        assertThat(spyPostsService.getPostsByMemberId_argumentPage).isEqualTo(1);
        assertThat(spyPostsService.getPostsByMemberId_argumentSize).isEqualTo(1);
    }

    @Test
    void getMyPosts_passesDefaultPageSizeToService() throws Exception {
        mockMvc.perform(get("/api/v1/posts"));

        assertThat(spyPostsService.getPostsByMemberId_argumentPage).isEqualTo(0);
        assertThat(spyPostsService.getPostsByMemberId_argumentSize).isEqualTo(20);
    }

    @Test
    void getPostsByTag_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/posts/search")
                        .param("tag", ""))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsByTag_returnsPostsDtoList() throws Exception {
        spyPostsService.getPostsByTag_returnValue = List.of(
                new PostsDto(
                        1L,
                        PostsFirstCategory.NO1,
                        PostsSecondCategory.NO1,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts/search")
                        .param("tag", ""))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].firstCategory", equalTo("NO1")))
                .andExpect(jsonPath("$[0].secondCategory", equalTo("NO1")))
                .andExpect(jsonPath("$[0].content", equalTo("content")))
                .andExpect(jsonPath("$[0].tags", contains("1", "2")))
                .andExpect(jsonPath("$[0].disclosure", equalTo(true)))
                .andExpect(jsonPath("$[0].views", equalTo(1)))
                .andExpect(jsonPath("$[0].createdAt", equalTo("2022-04-24 12:30:30")))
        ;
    }

    @Test
    void getPostsByTag_passesTagdAndPageSizeToService() throws Exception {
        mockMvc.perform(get("/api/v1/posts/search")
                .param("tag", "tag")
                .param("page", "1")
                .param("size", "1"));

        assertThat(spyPostsService.getPostsByTag_argumentTag).isEqualTo("tag");
        assertThat(spyPostsService.getPostsByTag_argumentPage).isEqualTo(1);
        assertThat(spyPostsService.getPostsByTag_argumentSize).isEqualTo(1);
    }

}