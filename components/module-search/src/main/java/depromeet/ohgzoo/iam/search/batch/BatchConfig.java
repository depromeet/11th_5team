package depromeet.ohgzoo.iam.search.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PostsClient postsClient;
    private final SearchRepository searchRepository;

    @Bean
    public Job collectJob() {
        return jobBuilderFactory.get("collectJob")
                .start(collectPostsStep())
                .build();
    }

    @Bean
    public Step collectPostsStep() {
        return stepBuilderFactory.get("inquiryPosts")
                .<RemotePosts, SearchEntity>chunk(3)
                .reader(inquiryPosts())
                .processor(processPosts())
                .writer(writePosts())
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<SearchEntity> writePosts() {
        /* update 된 애들만 저장할라 했는데, updatedAt을 안주네... 추후 수정 */
        return searchRepository::saveAll;
    }

    @Bean
    @StepScope
    public ItemProcessor<RemotePosts, SearchEntity> processPosts() {
        return posts -> new SearchEntity(posts.getId(),
                posts.getMemberId(),
                posts.getFirstCategory(),
                posts.getSecondCategory(),
                posts.getContent(),
                posts.getTags(),
                posts.getViews(),
                posts.getCreatedAt());
    }

    @Bean
    @StepScope
    public ItemReader<RemotePosts> inquiryPosts() {
        ResultModel<List<RemotePosts>> postResult = postsClient.getPosts();
        return new ListItemReader<>(postResult.getData());
    }
}
