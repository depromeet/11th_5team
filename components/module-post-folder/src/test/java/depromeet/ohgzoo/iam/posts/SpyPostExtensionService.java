package depromeet.ohgzoo.iam.posts;

public class SpyPostExtensionService implements PostExtensionService {
    public CreatePostRequest createPost_argumentRequest;
    public Long createPost_argumentMemberId;

    @Override
    public void createPost(Long memberId, CreatePostRequest request) {
        createPost_argumentMemberId = memberId;
        createPost_argumentRequest = request;
    }
}
