package depromeet.ohgzoo.iam.posts;


import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostsApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
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
    void updatePosts_isOk() throws Exception {
        mockMvc.perform(patch("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePosts_passesRequestToService() throws Exception {
        UpdatePostsRequest request = UpdatePostsRequest.builder().secondCategory(SecondCategory.NO1)
                .content("content").tags(List.of("tag")).disclosure(true).build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getSecondCategory()).isEqualTo(SecondCategory.NO1);
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getContent()).isEqualTo("content");
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getTags()).isEqualTo(List.of("tag"));
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getDisclosure()).isEqualTo(true);
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
                        "1",
                        FirstCategory.NO1,
                        SecondCategory.NO1,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
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
                        "1",
                        FirstCategory.NO1,
                        SecondCategory.NO1,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts/search")
                        .param("tag", ""))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
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
    void getPostsByTag_passesTagAndPageSizeToService() throws Exception {
        mockMvc.perform(get("/api/v1/posts/search")
                .param("tag", "tag")
                .param("page", "1")
                .param("size", "1"));

        assertThat(spyPostsService.getPostsByTag_argumentTag).isEqualTo("tag");
        assertThat(spyPostsService.getPostsByTag_argumentPage).isEqualTo(1);
        assertThat(spyPostsService.getPostsByTag_argumentSize).isEqualTo(1);
    }

    @Test
    void getPostsOrderByPopular_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/posts/popular"))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsOrderByPopular_returnsPostsDtoList() throws Exception {
        spyPostsService.getPostsOrderByPopular_returnValue = List.of(
                new PostsDto(
                        "1",
                        FirstCategory.NO1,
                        SecondCategory.NO1,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts/popular"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
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
    void getPostsOrderByPopular_passesTagAndPageSizeToService() throws Exception {
        mockMvc.perform(get("/api/v1/posts/popular")
                .param("page", "1")
                .param("size", "1"));

        assertThat(spyPostsService.getPostsOrderByPopular_argumentPage).isEqualTo(1);
        assertThat(spyPostsService.getPostsOrderByPopular_argumentSize).isEqualTo(1);
    }

    @Test
    void getRecentlyUnwrittenPosts_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/posts/temp"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecentlyUnwrittenPosts_passesMemberIdToService() throws Exception {
        spyJwtService.getSubject_returnValue = "1";

        mockMvc.perform(get("/api/v1/posts/temp"));

        assertThat(spyPostsService.getRecentlyUnwrittenPosts_argumentMemberId).isEqualTo(1L);
    }

    @Test
    void getRecentlyUnwrittenPosts_returnsPostsDto() throws Exception {
        spyPostsService.getRecentlyUnwrittenPosts_returnValue = new PostsDto(
                "1",
                FirstCategory.NO1,
                SecondCategory.NO1,
                "content",
                List.of("1", "2"), true, 1,
                LocalDateTime.of(2022, 4, 24, 12, 30, 30));

        mockMvc.perform(get("/api/v1/posts/temp"))
                .andExpect(jsonPath("$.id", equalTo("1")))
                .andExpect(jsonPath("$.firstCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.secondCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.content", equalTo("content")))
                .andExpect(jsonPath("$.tags", contains("1", "2")))
                .andExpect(jsonPath("$.disclosure", equalTo(true)))
                .andExpect(jsonPath("$.views", equalTo(1)))
                .andExpect(jsonPath("$.createdAt", equalTo("2022-04-24 12:30:30")))
        ;
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

        assertThat(spyPostsService.increaseViews_argumentPostId).isEqualTo("1");
    }
}