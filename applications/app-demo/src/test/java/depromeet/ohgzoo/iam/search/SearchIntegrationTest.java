package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.search.batch.PostsClient;
import depromeet.ohgzoo.iam.search.batch.RemotePosts;
import depromeet.ohgzoo.iam.search.batch.ResultModel;
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

    @Test
    void search() throws Exception {
        given(postsClient.getPosts()).willReturn(ResultModel.of(
                List.of(
                        new RemotePosts("id1", 1L, "first category", "second category", "content1", List.of("tag1", "tag2"), 0, LocalDateTime.now()),
                        new RemotePosts("id2", 2L, "first category", "second category", "content2", List.of("tag1", "tag2"), 0, LocalDateTime.now())
                )));

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();

        jobLauncherTestUtils.launchJob(jobParameters);

        mockMvc.perform(get("/api/v1/search")
                        .param("keyword", "content"))
                .andExpect(status().isOk());
    }
}
