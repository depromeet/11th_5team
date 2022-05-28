package depromeet.ohgzoo.iam.posts;

import java.util.List;

public interface PostExtensionService {
    CreatePostResult createPost(Long memberId, CreatePostRequest request);

    void deletePosts(Long memberId, List<String> postIds);

    void updatePost(String postId, UpdatePostRequest request, Long memberId);

    void increaseViews(String postId);
}
