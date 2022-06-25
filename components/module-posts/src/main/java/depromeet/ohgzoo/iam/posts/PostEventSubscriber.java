package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.member.MemberDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.IncreaseViewEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.PostUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostEventSubscriber {
    private final PostsService postsService;

    @EventListener
    public void handlePostCreateEvent(PostCreateEvent event) {
        postsService.createPosts(event.getMemberId(), mapToCreateRequest(event));
    }

    @EventListener
    public void handlePostUpdateEvent(PostUpdateEvent event) {
        postsService.updatePosts(event.getPostId(), mapToUpdateRequest(event), event.getMemberId());
    }

    @EventListener
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        postsService.deletePosts(event.getPostIds(), event.getMemberId());
    }

    @EventListener
    public void handleMemberDeleteEvent(MemberDeleteEvent memberDeleteEvent) {
        postsService.deleteAllPosts(memberDeleteEvent.getMemberId());
    }

    @EventListener
    public void handleIncreaseViewEvent(IncreaseViewEvent event) {
        postsService.increaseViews(event.getPostId());
    }

    private CreatePostsRequest mapToCreateRequest(PostCreateEvent event) {
        return new CreatePostsRequest(
                event.getPostId(),
                event.getFirstCategory(),
                event.getSecondCategory(),
                event.getContent(),
                event.getTags(),
                event.isDisclosure());
    }

    private UpdatePostsRequest mapToUpdateRequest(PostUpdateEvent event) {
        return new UpdatePostsRequest(
                event.getSecondCategory(),
                event.getContent(),
                event.getTags(),
                event.isDisclosure());
    }
}
