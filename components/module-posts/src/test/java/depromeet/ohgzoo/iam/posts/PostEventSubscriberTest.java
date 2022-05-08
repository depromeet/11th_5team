package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostEventSubscriberTest {
    private PostEventSubscriber postEventSubscriber;
    private SpyPostsService spyPostsService;

    @BeforeEach
    void setUp() {
        spyPostsService = new SpyPostsService();
        postEventSubscriber = new PostEventSubscriber(spyPostsService);
    }

    @Test
    void handlePostCreateEvent_passesCreateRequestToService() {
        PostCreateEvent givenEvent = new PostCreateEvent(this,
                1L,
                FirstCategory.ANGRY,
                SecondCategory.UPSET,
                "content",
                List.of("tag1", "tag2", "tag3"),
                true,
                null, null);

        postEventSubscriber.handlePostCreateEvent(givenEvent);

        assertThat(spyPostsService.createPosts_argumentMemberId).isEqualTo(1L);
        assertThat(spyPostsService.createPosts_argumentRequest.getFirstCategory()).isEqualTo(FirstCategory.ANGRY);
        assertThat(spyPostsService.createPosts_argumentRequest.getSecondCategory()).isEqualTo(SecondCategory.UPSET);
        assertThat(spyPostsService.createPosts_argumentRequest.getContent()).isEqualTo("content");
        assertThat(spyPostsService.createPosts_argumentRequest.getTags()).containsExactly("tag1", "tag2", "tag3");
        assertThat(spyPostsService.createPosts_argumentRequest.isDisclosure()).isTrue();
    }

    @Test
    void handlePostDeleteEvent_passesPostIdsToService() {
        PostDeleteEvent givenEvent = new PostDeleteEvent(this,
                1L, List.of("1", "2", "3"));

        postEventSubscriber.handlePostDeleteEvent(givenEvent);

        assertThat(spyPostsService.deletePosts_argumentMemberId).isEqualTo(1L);
        assertThat(spyPostsService.deletePosts_argumentPostsId).isEqualTo(List.of("1", "2", "3"));
    }
}