package depromeet.ohgzoo.iam.posts;

import java.util.List;

public class SpyPostExtensionService implements PostExtensionService {
    public CreatePostRequest createPost_argumentRequest;
    public List<String> deletePosts_argumentPostsId;
    public Long createPost_argumentMemberId;
    public Long deletePosts_argumentMemberId;

    @Override
    public void createPost(Long memberId, CreatePostRequest request) {
        createPost_argumentMemberId = memberId;
        createPost_argumentRequest = request;
    }

    @Override
    public void deletePosts(Long memberId, List<String> postIds) {
        deletePosts_argumentMemberId = memberId;
        deletePosts_argumentPostsId = postIds;
    }
}
