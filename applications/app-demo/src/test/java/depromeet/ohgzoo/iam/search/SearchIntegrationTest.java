package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.search.batch.PostsClient;
import depromeet.ohgzoo.iam.search.batch.RemotePosts;
import depromeet.ohgzoo.iam.search.batch.ResultModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBatchTest
public class SearchIntegrationTest extends IntegrationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @MockBean
    private PostsClient postsClient;

    @BeforeEach
    void setUp() throws Exception {
        given(postsClient.getPosts()).willReturn(ResultModel.of(
                List.of(
                        new RemotePosts("id1", 1L, "first category", "second category", "content1", List.of("tag1", "tag2"), 0, LocalDateTime.now()),
                        new RemotePosts("id2", 2L, "first category", "second category", "content2", List.of("tag2", "tag3"), 0, LocalDateTime.now())
                )));

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();

        jobLauncherTestUtils.launchJob(jobParameters);
    }

    @Test
    void search() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                        .param("keyword", "content"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByTag() throws Exception {
        mockMvc.perform(get("/api/v1/search/tag")
                        .param("keyword", "tag1"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByTag_orderByCreatedAt() throws Exception {
        mockMvc.perform(get("/api/v1/search/tag")
                        .param("keyword", "tag1")
                        .param("order", "new"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByCategory() throws Exception {
        mockMvc.perform(get("/api/v1/search/category")
                        .param("keyword", "first"))
                .andExpect(status().isOk());
    }

    @Test
    void getRankingTags() throws Exception {
        mockMvc.perform(get("/api/v1/search/ranking/tag"))
                .andExpect(status().isOk());
    }
}
