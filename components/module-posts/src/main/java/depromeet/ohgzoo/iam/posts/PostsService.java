package depromeet.ohgzoo.iam.posts;

import java.util.List;

public interface PostsService {
    void updatePosts(Long postId, UpdatePostsRequest request, Long memberId);
    void deletePosts(List<Long> postIds, Long memberId);
    CreatePostsResult createPosts(Long memberId, CreatePostsRequest request);
    List<PostsDto> getPostsByMemberId(Long memberId, int page, int size);
    List<PostsDto> getPostsByTag(String tag, int page, int size);
    List<PostsDto> getPostsOrderByPopular(int page, int size);
    PostsDto getRecentlyUnwrittenPosts(Long memberId);
    void increaseViews(Long postId);
    PostsDto getPostsById(Long postId);
    List<PostsDto> getAllPosts();
}