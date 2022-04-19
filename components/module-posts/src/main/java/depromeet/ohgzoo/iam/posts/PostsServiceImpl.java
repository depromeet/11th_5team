package depromeet.ohgzoo.iam.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public CreatePostsResult createPosts(CreatePostsRequest request) {
        Posts post = Posts.builder().firstCategory(request.getFirstCategory())
                .secondCategory(request.getSecondCategory()).content(request.getContent())
                .tags(request.getTags()).disclosure(request.isDisclosure()).build();
        postsRepository.save(post);
        return new CreatePostsResult(post.getId());
    }
}
