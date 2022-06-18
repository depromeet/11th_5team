package depromeet.ohgzoo.iam.search.batch;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.search.SpySearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BatchConfigTest {

    private BatchConfig sut;
    private SpyPostClient spyPostClient;
    private SpySearchRepository spySearchRepository;

    @BeforeEach
    void setUp() {
        spyPostClient = new SpyPostClient();
        spySearchRepository = new SpySearchRepository();
        sut = new BatchConfig(null, null, spyPostClient, spySearchRepository);
    }

    @Test
    void inquiryPosts_callsGetPostsInPostClient() {
        sut.inquiryPosts();

        assertThat(spyPostClient.getPosts_wasCalled).isTrue();
    }

    @Test
    void processPosts_convertToSearchEntity() throws Exception {
        ItemProcessor<RemotePosts, SearchEntity> processor = sut.processPosts();

        RemotePosts givenPosts = new RemotePosts("id", 1L, FirstCategory.SADNESS, SecondCategory.SADNESS, "content", List.of("tag1"), 1);

        SearchEntity result = processor.process(givenPosts);

        assertThat(result.getId()).isEqualTo("id");
        assertThat(result.getMemberId()).isEqualTo(1L);
        assertThat(result.getFirstCategory()).isEqualTo(FirstCategory.SADNESS);
        assertThat(result.getSecondCategory()).isEqualTo(SecondCategory.SADNESS);
        assertThat(result.getTags()).contains("tag1");
        assertThat(result.getViews()).isEqualTo(1);
    }

    @Test
    void writePosts_callsSaveAllInRepository() throws Exception {
        ItemWriter<SearchEntity> writer = sut.writePosts();

        List<SearchEntity> givenList = Collections.emptyList();
        writer.write(givenList);

        assertThat(spySearchRepository.saveAll_argumentList).isEqualTo(givenList);
    }
}