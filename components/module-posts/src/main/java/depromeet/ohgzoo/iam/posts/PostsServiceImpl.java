package depromeet.ohgzoo.iam.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional
    public void updatePosts(Long postId, UpdatePostsRequest request, Long memberId) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        // 권한 체크 후 예외 터뜨리는 로직 필요(posts에서 userId를 갖고 있을건지, 연관관계를 맺을건지)

        post.update(request);
    }

    @Override
    @Transactional
    public void deletePosts(List<Long> postIds, Long memberId) {
        // 권한 체크 후 예외 터뜨리는 로직 필요(posts에서 userId를 갖고 있을건지, 연관관계를 맺을건지)

        postsRepository.bulkDeletePosts(postIds);
    }
}
