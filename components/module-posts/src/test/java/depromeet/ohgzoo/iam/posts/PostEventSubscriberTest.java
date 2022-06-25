package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.member.MemberDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.IncreaseViewEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.PostUpdateEvent;
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
                FirstCategory.SADNESS,
                SecondCategory.SADNESS,
                "content",
                List.of("tag1", "tag2", "tag3"),
                true,
                null, null);

        postEventSubscriber.handlePostCreateEvent(givenEvent);

        assertThat(spyPostsService.createPosts_argumentMemberId).isEqualTo(1L);
        assertThat(spyPostsService.createPosts_argumentRequest.getFirstCategory()).isEqualTo(FirstCategory.SADNESS);
        assertThat(spyPostsService.createPosts_argumentRequest.getSecondCategory()).isEqualTo(SecondCategory.SADNESS);
        assertThat(spyPostsService.createPosts_argumentRequest.getContent()).isEqualTo("content");
        assertThat(spyPostsService.createPosts_argumentRequest.getTags()).containsExactly("tag1", "tag2", "tag3");
        assertThat(spyPostsService.createPosts_argumentRequest.isDisclosure()).isTrue();
    }

    @Test
    void handlePostUpdateEvent_passesUpdateRequestToService() {
        PostUpdateEvent givenEvent = new PostUpdateEvent(this,
                1L,
                "1",
                SecondCategory.SADNESS,
                "content",
                List.of("tag1", "tag2", "tag3"),
                true,
                null);

        postEventSubscriber.handlePostUpdateEvent(givenEvent);

        assertThat(spyPostsService.updatePostsRequest_argumentMemberId).isEqualTo(1L);
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getSecondCategory()).isEqualTo(SecondCategory.SADNESS);
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getContent()).isEqualTo("content");
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getTags()).containsExactly("tag1", "tag2", "tag3");
        assertThat(spyPostsService.updatePostsRequest_argumentRequest.getDisclosure()).isTrue();
    }

    @Test
    void handlePostDeleteEvent_passesPostIdsToService() {
        PostDeleteEvent givenEvent = new PostDeleteEvent(this,
                1L, List.of("1", "2", "3"));

        postEventSubscriber.handlePostDeleteEvent(givenEvent);

        assertThat(spyPostsService.deletePosts_argumentMemberId).isEqualTo(1L);
        assertThat(spyPostsService.deletePosts_argumentPostsId).isEqualTo(List.of("1", "2", "3"));
    }

    @Test
    void handleIncreaseViewEvent_passesPostIdToService() {
        IncreaseViewEvent givenEvent = new IncreaseViewEvent(this, "1");
        postEventSubscriber.handleIncreaseViewEvent(givenEvent);
        assertThat(spyPostsService.increaseViews_argumentPostId).isEqualTo("1");
    }

    @Test
    void handlePostDeleteAllEvent_passesMemberIdToService() {
        MemberDeleteEvent givenEvent = new MemberDeleteEvent(this, 1L);
        postEventSubscriber.handlePostDeleteAllEvent(givenEvent);

        assertThat(spyPostsService.deleteAllPosts_argumentMemberId).isEqualTo(1L);
    }
}