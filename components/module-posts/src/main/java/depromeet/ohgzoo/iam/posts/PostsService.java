package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsService {
    void updatePosts(String postId, UpdatePostsRequest request, Long memberId);
    void deletePosts(List<String> postIds, Long memberId);
    CreatePostsResult createPosts(Long memberId, CreatePostsRequest request);
    List<PostsDto> getPostsByMemberId(Long memberId, int page, int size);
    List<PostsDto> getPostsByTag(String tag, int page, int size);
    List<PostsDto> getPostsOrderByPopular(int page, int size);
    List<PostsDto> getRecentlyUnwrittenPosts(Long memberId);
    void increaseViews(String postId);
    OnePostsDto getPostsById(Long memberId, String postId);
    List<PostsDto> getAllPosts();

    List<CategoryResponse> getCategories(Long memberId);

    CategoryItemsResponse getCategoryItems(Long memberId, Integer categoryId, Pageable pageable);
}