package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostExtensionServiceImplTest {
    private PostExtensionService postExtensionService;
    private SpyApplicationEventPublisher spyEventPublisher;

    @BeforeEach
    void setUp() {
        spyEventPublisher = new SpyApplicationEventPublisher();
        postExtensionService = new PostExtensionServiceImpl(spyEventPublisher);
    }

    @Test
    void createPost_passesPostCreateEventToEventPublisher() {
        CreatePostRequest givenRequest = new CreatePostRequest(
                FirstCategory.NO1,
                SecondCategory.Idk,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true,
                1L
        );

        postExtensionService.createPost(1L, givenRequest);

        PostCreateEvent expected = new PostCreateEvent(
                postExtensionService,
                1L,
                FirstCategory.NO1,
                SecondCategory.Idk,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true,
                1L
        );

        assertThat(spyEventPublisher.publishEvent_argumentEvent).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}