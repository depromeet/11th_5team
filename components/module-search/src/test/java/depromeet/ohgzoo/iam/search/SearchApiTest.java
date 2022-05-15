package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import depromeet.ohgzoo.iam.jwt.SpyJwtService;
import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchApiTest {

    private MockMvc mockMvc;
    private SpySearchService spySearchService;
    private SpyJwtService spyJwtService;

    @BeforeEach
    void setUp() {
        spyJwtService = new SpyJwtService();
        spySearchService = new SpySearchService();
        mockMvc = MockMvcBuilders.standaloneSetup(new SearchApi(spySearchService))
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(spyJwtService)).build();
    }

    @Test
    void search_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                        .param("keyword", ""))
                .andExpect(status().isOk());
    }

    @Test
    void search_returnsSearchResult() throws Exception {
        spySearchService.search_returnValue = new SearchModel(
                "id",
                "NO1",
                "NO1",
                "content",
                List.of("1", "2"),
                1,
                LocalDateTime.of(2022, 4, 24, 12, 30, 30),
                true
        );

        mockMvc.perform(get("/api/v1/search")
                        .param("keyword", ""))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(1)))
                .andExpect(jsonPath("$.posts[0].id", equalTo("id")))
                .andExpect(jsonPath("$.posts[0].firstCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.posts[0].secondCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.posts[0].content", equalTo("content")))
                .andExpect(jsonPath("$.posts[0].tags", contains("1", "2")))
                .andExpect(jsonPath("$.posts[0].views", equalTo(1)))
                .andExpect(jsonPath("$.posts[0].createdAt", equalTo("2022-04-24 12:30:30")))
                .andExpect(jsonPath("$.posts[0].my", equalTo(true)))
        ;
    }

    @Test
    void search_passesKeywordToService() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                .param("keyword", "test"));

        assertThat(spySearchService.search_argumentKeyword).isEqualTo("test");
    }

    @Test
    void search_passesMemberIdToService_whenLogined() throws Exception {
        spyJwtService.getSubject_returnValue = "1";

        mockMvc.perform(get("/api/v1/search")
                .param("keyword", ""));

        assertThat(spySearchService.search_argumentMemberId).isEqualTo(1L);
    }

    @Test
    void search_passesNullToService_whenNotLogined() throws Exception {
        spyJwtService.getSubject_returnValue = null;

        mockMvc.perform(get("/api/v1/search")
                .param("keyword", ""));

        assertThat(spySearchService.search_argumentMemberId).isNull();
    }

    @Test
    void searchByTag_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/search/tag")
                        .param("keyword", ""))
                .andExpect(status().isOk());
    }

    @Test
    void searchByTag_returnsSearchResult() throws Exception {
        spySearchService.searchByTag_returnValue = List.of(new SearchModel(
                "id",
                "NO1",
                "NO1",
                "content",
                List.of("1", "2"),
                1,
                LocalDateTime.of(2022, 4, 24, 12, 30, 30),
                true
        ));

        mockMvc.perform(get("/api/v1/search/tag")
                        .param("keyword", ""))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(1)))
                .andExpect(jsonPath("$.posts[0].id", equalTo("id")))
                .andExpect(jsonPath("$.posts[0].firstCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.posts[0].secondCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.posts[0].content", equalTo("content")))
                .andExpect(jsonPath("$.posts[0].tags", contains("1", "2")))
                .andExpect(jsonPath("$.posts[0].views", equalTo(1)))
                .andExpect(jsonPath("$.posts[0].createdAt", equalTo("2022-04-24 12:30:30")))
                .andExpect(jsonPath("$.posts[0].my", equalTo(true)))
        ;
    }

    @Test
    void searchByTag_passesOrderToService() throws Exception {
        mockMvc.perform(get("/api/v1/search/tag")
                .param("keyword", "")
                .param("order", "test"))
        ;

        assertThat(spySearchService.searchByTag_argumentOrder).isEqualTo("test");
    }

    @Test
    void searchByTag_passesKeywordToService() throws Exception {
        mockMvc.perform(get("/api/v1/search/tag")
                .param("keyword", "test"));

        assertThat(spySearchService.searchByTag_argumentKeyword).isEqualTo("test");
    }

    @Test
    void searchByTag_passesMemberIdToService_whenLogined() throws Exception {
        spyJwtService.getSubject_returnValue = "1";

        mockMvc.perform(get("/api/v1/search/tag")
                .param("keyword", ""));

        assertThat(spySearchService.searchByTag_argumentMemberId).isEqualTo(1L);
    }

    @Test
    void searchByTag_passesNullToService_whenNotLogined() throws Exception {
        spyJwtService.getSubject_returnValue = null;

        mockMvc.perform(get("/api/v1/search/tag")
                .param("keyword", ""));

        assertThat(spySearchService.searchByTag_argumentMemberId).isNull();
    }

    @Test
    void searchByCategory_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/search/category")
                        .param("keyword", ""))
                .andExpect(status().isOk());
    }

    @Test
    void searchByCategory_returnsSearchResult() throws Exception {
        spySearchService.searchByCategory_returnValue = new SearchModel(
                "id",
                "NO1",
                "NO1",
                "content",
                List.of("1", "2"),
                1,
                LocalDateTime.of(2022, 4, 24, 12, 30, 30),
                true
        );

        mockMvc.perform(get("/api/v1/search/category")
                        .param("keyword", ""))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(1)))
                .andExpect(jsonPath("$.posts[0].id", equalTo("id")))
                .andExpect(jsonPath("$.posts[0].firstCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.posts[0].secondCategory", equalTo("NO1")))
                .andExpect(jsonPath("$.posts[0].content", equalTo("content")))
                .andExpect(jsonPath("$.posts[0].tags", contains("1", "2")))
                .andExpect(jsonPath("$.posts[0].views", equalTo(1)))
                .andExpect(jsonPath("$.posts[0].createdAt", equalTo("2022-04-24 12:30:30")))
                .andExpect(jsonPath("$.posts[0].my", equalTo(true)))
        ;
    }

    @Test
    void searchByCategory_passesKeywordToService() throws Exception {
        mockMvc.perform(get("/api/v1/search/category")
                .param("keyword", "test"));

        assertThat(spySearchService.searchByCategory_argumentKeyword).isEqualTo("test");
    }

    @Test
    void searchByCategory_passesMemberIdToService_whenLogined() throws Exception {
        spyJwtService.getSubject_returnValue = "1";

        mockMvc.perform(get("/api/v1/search/category")
                .param("keyword", ""));

        assertThat(spySearchService.searchByCategory_argumentMemberId).isEqualTo(1L);
    }

    @Test
    void searchByCategory_passesNullToService_whenNotLogined() throws Exception {
        spyJwtService.getSubject_returnValue = null;

        mockMvc.perform(get("/api/v1/search/category")
                .param("keyword", ""));

        assertThat(spySearchService.searchByCategory_argumentMemberId).isNull();
    }

}