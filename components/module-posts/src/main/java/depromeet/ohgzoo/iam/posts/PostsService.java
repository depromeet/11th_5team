package depromeet.ohgzoo.iam.posts;

import java.util.List;

public interface PostsService {
    CreatePostsResult createPosts(CreatePostsRequest request);

    void updatePosts(Long postId, UpdatePostsRequest request, Long memberId);

    void deletePosts(List<Long> postIds, Long memberId);
}