package depromeet.ohgzoo.iam.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public Page<PostsDto> findPostsByTag(String tag, Pageable pageable) {
        return postsRepository.findByTagsOrderByIdDesc(tag, pageable).map(PostsDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PostsDto> findPostsOrderByPopular(Pageable pageable) {
        return postsRepository.findAllOrderByViewDesc(pageable).map(PostsDto::new);
    }

    @Transactional(readOnly = true)
    public PostsDto findRecentPostWhereSecondCategoryIsNull(Long memberId) {
        Posts posts = postsRepository
                .findTop1ByMemberIdAndSecondCategoryAndCreatedAtGreaterThanEqualOrderByIdDesc(memberId, PostsSecondCategory.NO3, LocalDateTime.now().minusDays(7))
                .orElseThrow(PostsNotFoundException::new);
        return new PostsDto(posts);
    }
}
