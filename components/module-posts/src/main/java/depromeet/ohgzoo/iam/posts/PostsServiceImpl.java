package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.SecondCategory;
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
        Posts post = Posts.builder()
                .id(request.getPostId())
                .memberId(memberId)
                .firstCategory(request.getFirstCategory())
                .secondCategory(request.getSecondCategory())
                .content(request.getContent())
                .tags(request.getTags())
                .disclosure(request.isDisclosure()).build();
        postsRepository.save(post);
        return new CreatePostsResult(post.getId());
    }

    @Override
    @Transactional
    public void updatePosts(String postId, UpdatePostsRequest request, Long memberId) {
        Posts post = findPostById(postId);

        if (!canAccess(post.getMemberId(), memberId)) throw new AccessDeniedException();

        post.update(request);
    }

    @Override
    @Transactional
    public void deletePosts(List<String> postIds, Long memberId) {
        List<Posts> all = postsRepository.findAll();
        for (Posts posts : all) {
            System.out.println("posts.getId() = " + posts.getId());
        }


        for (String postId : postIds) {
            Posts post = findPostById(postId);
            if (!canAccess(post.getMemberId(), memberId)) throw new AccessDeniedException();
        }

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
                .filter(posts -> SecondCategory.Unwritten.equals(posts.getSecondCategory()))
                .filter(posts -> LocalDateTime.now().minusDays(7).isBefore(posts.getCreatedAt()))
                .sorted(Comparator.comparing(Posts::getId).reversed())
                .findFirst()
                .orElseThrow(PostsNotFoundException::new);

        return new PostsDto(first);
    }

    @Override
    @Transactional
    public void increaseViews(String postId) {
        Posts post = findPostById(postId);
        post.increaseViews();
    }

    private Posts findPostById(String postId) {
        return postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
    }

    private boolean canAccess(Long ownerId, Long memberId) {
        return ownerId.equals(memberId);
    }

    @Transactional(readOnly = true)
    public PostsDto getPostsById(String postId) {
        return new PostsDto(postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new));
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