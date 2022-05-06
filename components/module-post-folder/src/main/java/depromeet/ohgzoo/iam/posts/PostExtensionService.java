package depromeet.ohgzoo.iam.posts;

public interface PostExtensionService {
    void createPost(Long memberId, CreatePostRequest request);
}
