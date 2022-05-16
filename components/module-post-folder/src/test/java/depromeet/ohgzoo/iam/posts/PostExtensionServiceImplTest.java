package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.postEvent.IncreaseViewEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PostExtensionServiceImplTest {
    private PostExtensionService postExtensionService;
    private SpyApplicationEventPublisher spyEventPublisher;
    private StubUUIDProvider stubUUIDProvider;

    @BeforeEach
    void setUp() {
        stubUUIDProvider = new StubUUIDProvider();
        spyEventPublisher = new SpyApplicationEventPublisher();
        postExtensionService = new PostExtensionServiceImpl(spyEventPublisher, stubUUIDProvider);
    }

    @Test
    void createPost_passesPostCreateEventToEventPublisher() {
        UUID givenUUID = UUID.randomUUID();
        stubUUIDProvider.randomUUID_returnValue = givenUUID;

        CreatePostRequest givenRequest = new CreatePostRequest(
                FirstCategory.SADNESS,
                SecondCategory.DONTKNOW,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true,
                1L
        );

        postExtensionService.createPost(1L, givenRequest);

        PostCreateEvent expected = new PostCreateEvent(
                postExtensionService,
                1L,
                FirstCategory.SADNESS,
                SecondCategory.DONTKNOW,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true,
                1L,
                givenUUID.toString()
        );

        assertThat((PostCreateEvent) spyEventPublisher.publishEvent_argumentEvent).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void createPost_returnsPostCreateResult() {
        UUID givenUUID = UUID.randomUUID();
        stubUUIDProvider.randomUUID_returnValue = givenUUID;

        CreatePostRequest givenRequest = new CreatePostRequest(
                FirstCategory.SADNESS,
                SecondCategory.DONTKNOW,
                "givenContent",
                List.of("tag1", "tag2", "tag3"),
                true,
                1L
        );

        CreatePostResult result = postExtensionService.createPost(1L, givenRequest);

        assertThat(result.getPostId()).isEqualTo(givenUUID.toString());
    }

    @Test
    void deletePosts_passesPostDeleteEventToEventPublisher() {
        postExtensionService.deletePosts(1L, List.of("1", "2", "3"));

        PostDeleteEvent expected = new PostDeleteEvent(
                postExtensionService,
                1L,
                List.of("1", "2", "3")
        );

        assertThat((PostDeleteEvent) spyEventPublisher.publishEvent_argumentEvent).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void increaseViews_passesIncreaseViewEventToEventPublisher() {
        postExtensionService.increaseViews("1");

        IncreaseViewEvent expected = new IncreaseViewEvent(postExtensionService, "1");

        assertThat((IncreaseViewEvent) spyEventPublisher.publishEvent_argumentEvent).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}