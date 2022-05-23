package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.CategoryService;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.posts.CategoryItemsResponse.CategoryItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;
    private final CategoryService categoryService;

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
    public List<PostsDto> getRecentlyUnwrittenPosts(Long memberId) {
        return postsRepository.findByMemberId(memberId)
                .stream()
                .filter(posts -> SecondCategory.Unwritten.equals(posts.getSecondCategory()))
                .filter(posts -> LocalDateTime.now().minusDays(7).isBefore(posts.getCreatedAt()))
                .sorted(Comparator.comparing(Posts::getId).reversed())
                .map(PostsDto::new)
                .collect(Collectors.toList());
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
    public OnePostsDto getPostsById(Long memberId, String postId) {
        Posts posts = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        if (posts.getMemberId().equals(memberId)) return new OnePostsDto(new PostsDto(posts), true);
        else return new OnePostsDto(new PostsDto(posts), false);
    }

    @Transactional(readOnly = true)
    public List<PostsDto> getAllPosts() {
        return postsRepository.findAll()
                .stream()
                .map(PostsDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories(Long memberId) {
        List<Posts> postsList = postsRepository.findByMemberId(memberId);
        List<SecondCategory> categories = categoryService.secondCategoryList();

        return categories.stream()
                .map(category -> new CategoryResponse(getCategoryContainsCount(postsList, category), category))
                .collect(Collectors.toList());
    }

    private int getCategoryContainsCount(List<Posts> postsList, SecondCategory category) {
        return (int) postsList.stream()
                .filter(posts -> posts.containsCategory(category))
                .count();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryItemsResponse getCategoryItems(Long memberId, Integer categoryId, Pageable pageable) {
        SecondCategory category = Arrays.stream(SecondCategory.values())
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        List<Posts> posts = postsRepository.findByMemberId(memberId)
                .stream()
                .filter(p -> p.containsCategory(category))
                .collect(Collectors.toList());

        List<CategoryItemDTO> categoryItemDTOList = posts.stream()
                .sorted(getCreatedDateReverseComparator())
                .skip(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .map(getPostsToCategoryItemDTOFunction())
                .collect(Collectors.toList());

        return new CategoryItemsResponse(categoryItemDTOList);
    }

    private Comparator<Posts> getCreatedDateReverseComparator() {
        return Comparator.comparing(Posts::getCreatedAt).reversed();
    }

    private Function<Posts, CategoryItemDTO> getPostsToCategoryItemDTOFunction() {
        return p -> CategoryItemDTO.builder()
                .postId(p.getId())
                .firstCategory(p.getFirstCategory())
                .secondCategory(p.getSecondCategory())
                .tags(p.getTags())
                .content(p.getContent())
                .createdDate(p.getCreatedAt())
                .build();
    }
}