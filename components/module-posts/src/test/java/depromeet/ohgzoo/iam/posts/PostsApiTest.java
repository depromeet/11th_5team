package depromeet.ohgzoo.iam.posts;


import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import depromeet.ohgzoo.iam.jwt.SpyJwtService;
import depromeet.ohgzoo.iam.posts.CategoryItemsResponse.CategoryItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(spyJwtService), new PageableHandlerMethodArgumentResolver())
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
        UpdatePostsRequest request = UpdatePostsRequest.builder().secondCategory(SecondCategory.SADNESS)
                .content("content").tags(List.of("tag")).disclosure(true).build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getSecondCategory()).isEqualTo(SecondCategory.SADNESS);
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
                        FirstCategory.SADNESS,
                        SecondCategory.SADNESS,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].firstCategory", equalTo("SADNESS")))
                .andExpect(jsonPath("$[0].secondCategory", equalTo("SADNESS")))
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
                        FirstCategory.SADNESS,
                        SecondCategory.SADNESS,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts/search")
                        .param("tag", ""))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].firstCategory", equalTo("SADNESS")))
                .andExpect(jsonPath("$[0].secondCategory", equalTo("SADNESS")))
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
                        FirstCategory.SADNESS,
                        SecondCategory.SADNESS,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30))
        );

        mockMvc.perform(get("/api/v1/posts/popular"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].firstCategory", equalTo("SADNESS")))
                .andExpect(jsonPath("$[0].secondCategory", equalTo("SADNESS")))
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
        spyPostsService.getRecentlyUnwrittenPosts_returnValue = List.of(
                new PostsDto(
                        "1",
                        FirstCategory.SADNESS,
                        SecondCategory.SADNESS,
                        "content",
                        List.of("1", "2"), true, 1,
                        LocalDateTime.of(2022, 4, 24, 12, 30, 30)));

        mockMvc.perform(get("/api/v1/posts/temp"))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].firstCategory", equalTo("SADNESS")))
                .andExpect(jsonPath("$[0].secondCategory", equalTo("SADNESS")))
                .andExpect(jsonPath("$[0].content", equalTo("content")))
                .andExpect(jsonPath("$[0].tags", contains("1", "2")))
                .andExpect(jsonPath("$[0].disclosure", equalTo(true)))
                .andExpect(jsonPath("$[0].views", equalTo(1)))
                .andExpect(jsonPath("$[0].createdAt", equalTo("2022-04-24 12:30:30")))
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

    @Test
    public void getCategories_isOk() throws Exception {
        mockMvc.perform(get("/api/v1/posts/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategories_passesMemberIdToService() throws Exception {
        spyJwtService.getSubject_returnValue = "1";
        mockMvc.perform(get("/api/v1/posts/categories"));

        assertThat(spyPostsService.categories_argumentMemberId).isEqualTo(1L);
    }

    @Test
    public void getCategories_returnsCategoryGetResponse() throws Exception {
        spyPostsService.categories_returnValue = List.of(new CategoryResponse(1, SecondCategory.ANXIOUS));

        mockMvc.perform(get("/api/v1/posts/categories"))
                .andExpect(jsonPath("$[0].count", equalTo(1)))
                .andExpect(jsonPath("$[0].categoryId", equalTo(11)))
                .andExpect(jsonPath("$[0].name", equalTo("불안해요")))
                .andExpect(jsonPath("$[0].image", equalTo("https://firebasestorage.googleapis.com/v0/b/cardna-29f5b.appspot.com/o/20220317_172729_412500527401_720x720.png?alt=media")))
                .andDo(print());
    }

    @Test
    public void getCategoryItems_isOk() throws Exception {
        mockMvc.perform(get("/api/v1/posts/categories/1")
                        .param("page", "1")
                        .param("size", "20"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryItems_passesMemberIdToService() throws Exception {
        spyJwtService.getSubject_returnValue = "1";
        mockMvc.perform(get("/api/v1/posts/categories/1")
                .param("page", "1")
                .param("size", "20"));
        assertThat(spyPostsService.getCategoryItems_argumentMemberId).isEqualTo(1L);
    }

    @Test
    public void getCategoryItems_returnsCategoryGetResponse() throws Exception {
        spyPostsService.getCategoryItems_returnValue = new CategoryItemsResponse(1, List.of(CategoryItemDTO.builder()
                .postId("postId")
                .firstCategory(FirstCategory.DONTKNOW)
                .secondCategory(SecondCategory.ANXIOUS)
                .tags(List.of("tag"))
                .content("content")
                .createdDate(LocalDateTime.of(2022, 5, 16, 17, 9, 30)).build()));


        mockMvc.perform(get("/api/v1/posts/categories/1"))
                .andExpect(jsonPath("$.totalCount", equalTo(1)))
                .andExpect(jsonPath("$.posts[0].postId", equalTo("postId")))
                .andExpect(jsonPath("$.posts[0].firstCategory", equalTo("DONTKNOW")))
                .andExpect(jsonPath("$.posts[0].secondCategory", equalTo("ANXIOUS")))
                .andExpect(jsonPath("$.posts[0].tags[0]", equalTo("tag")))
                .andExpect(jsonPath("$.posts[0].content", equalTo("content")))
                .andExpect(jsonPath("$.posts[0].createdDate", equalTo("2022-05-16 17:09:30")))
                .andDo(print());
    }
}