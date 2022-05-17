package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.postEvent.IncreaseViewEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostExtensionServiceImpl implements PostExtensionService {
    private final ApplicationEventPublisher eventPublisher;
    private final UUIDProvider uuidProvider;

    @Override
    public CreatePostResult createPost(Long memberId, CreatePostRequest request) {
        PostCreateEvent event = mapToEvent(memberId, request);
        eventPublisher.publishEvent(event);
        return new CreatePostResult(event.getPostId());
    }

    @Override
    public void deletePosts(Long memberId, List<String> postIds) {
        eventPublisher.publishEvent(new PostDeleteEvent(this, memberId, postIds));
    }

    @Override
    public void increaseViews(String postId) {
        eventPublisher.publishEvent(new IncreaseViewEvent(this, postId));
    }

    private PostCreateEvent mapToEvent(Long memberId, CreatePostRequest request) {
        String postId = getPostId();
        return new PostCreateEvent(this, memberId, request.getFirstCategory(), request.getSecondCategory(), request.getContent(), request.getTags(), request.isDisclosure(), request.getFolderId(), postId);
    }

    private String getPostId() {
        return uuidProvider.randomUUID().toString();
    }
}
