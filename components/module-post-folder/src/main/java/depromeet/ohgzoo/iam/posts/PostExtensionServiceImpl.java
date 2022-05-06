package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostExtensionServiceImpl implements PostExtensionService {
    private final ApplicationEventPublisher eventPublisher;
    private final UUIDProvider uuidProvider;

    @Override
    public void createPost(Long memberId, CreatePostRequest request) {
        eventPublisher.publishEvent(mapToEvent(memberId, request));
    }

    private PostCreateEvent mapToEvent(Long memberId, CreatePostRequest request) {
        String postId = getPostId();
        return new PostCreateEvent(this, memberId, request.getFirstCategory(), request.getSecondCategory(), request.getContent(), request.getTags(), request.isDisclosure(), request.getFolderId(), postId);
    }

    private String getPostId() {
        return uuidProvider.randomUUID().toString();
    }
}