package depromeet.ohgzoo.iam.posts;

import java.util.List;

public interface PostExtensionService {
    CreatePostResult createPost(Long memberId, CreatePostRequest request);

    void deletePosts(Long memberId, List<String> postIds);
}
