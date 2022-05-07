package depromeet.ohgzoo.iam.posts;

import java.util.List;

public interface PostExtensionService {
    void createPost(Long memberId, CreatePostRequest request);

    void deletePosts(Long memberId, List<String> postIds);
}
