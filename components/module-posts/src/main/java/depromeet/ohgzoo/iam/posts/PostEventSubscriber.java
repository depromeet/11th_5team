package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostEventSubscriber {
    private final PostsService postsService;

    @EventListener
    public void handlePostCreateEvent(PostCreateEvent event) {
        postsService.createPosts(event.getMemberId(), mapToRequest(event));
    }

    private CreatePostsRequest mapToRequest(PostCreateEvent event) {
        return new CreatePostsRequest(
                event.getPostId(),
                event.getFirstCategory(),
                event.getSecondCategory(),
                event.getContent(),
                event.getTags(),
                event.isDisclosure());
    }

    @EventListener
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        postsService.deletePosts(event.getPostIds(), event.getMemberId());
    }
}
