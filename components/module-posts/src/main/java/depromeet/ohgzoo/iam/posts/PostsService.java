package depromeet.ohgzoo.iam.posts;

import java.util.List;

public interface PostsService {
    CreatePostsResult createPosts(Long memberId, CreatePostsRequest request);
    List<PostsDto> getPostsByMemberId(Long memberId, int page, int size);
    List<PostsDto> getPostsByTag(String tag, int page, int size);
    List<PostsDto> getPostsOrderByPopular(int page, int size);
    PostsDto getRecentlyUnwrittenPosts(Long memberId);
}
