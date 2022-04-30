package depromeet.ohgzoo.iam.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public CreatePostsResult createPosts(Long memberId, CreatePostsRequest request) {
        Posts post = Posts.builder().memberId(memberId).firstCategory(request.getFirstCategory())
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

    @Transactional(readOnly = true)
    public List<PostsDto> getPostsByMemberId(Long memberId, int page, int size) {
        return postsRepository.findByMemberId(memberId)
                .stream()
                .skip(page)
                .limit(size)
                .map(PostsDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostsDto> getPostsByTag(String tag, int page, int size) {
        return postsRepository.findAll()
                .stream()
                .filter(posts -> posts.getTags().contains(tag))
                .skip(page)
                .limit(size)
                .map(PostsDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostsDto> getPostsOrderByPopular(int page, int size) {
        return postsRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Posts::getViews).reversed())
                .skip(page)
                .limit(size)
                .map(PostsDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostsDto getRecentlyUnwrittenPosts(Long memberId) {
        Posts first = postsRepository.findByMemberId(memberId)
                .stream()
                .filter(posts -> PostsSecondCategory.Unwritten.equals(posts.getSecondCategory()))
                .filter(posts -> LocalDateTime.now().minusDays(7).isBefore(posts.getCreatedAt()))
                .sorted(Comparator.comparingLong(Posts::getId).reversed())
                .findFirst()
                .orElseThrow(PostsNotFoundException::new);

        return new PostsDto(first);
    }

    @Transactional(readOnly = true)
    public PostsDto getPostsById(Long postId) {
        return new PostsDto(postsRepository.findById(postId).orElseThrow(PostNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<PostsDto> getAllPosts(int page, int size) {
        return postsRepository.findAll()
                .stream()
                .skip(page)
                .limit(size)
                .map(PostsDto::new)
                .collect(Collectors.toList());
    }
}
