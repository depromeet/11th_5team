package depromeet.ohgzoo.iam.posts;

import java.util.List;

public class SpyPostExtensionService implements PostExtensionService {
    public CreatePostRequest createPost_argumentRequest;
    public List<String> deletePosts_argumentPostsId;
    public Long createPost_argumentMemberId;
    public Long deletePosts_argumentMemberId;
    public CreatePostResult createPost_returnValue;
    public String increaseViews_argumentPostId;
    public Long updatePost_argumentMemberId;
    public String updatePost_argumentPostId;
    public UpdatePostRequest updatePost_argumentRequest;

    @Override
    public CreatePostResult createPost(Long memberId, CreatePostRequest request) {
        createPost_argumentMemberId = memberId;
        createPost_argumentRequest = request;
        return createPost_returnValue;
    }

    @Override
    public void deletePosts(Long memberId, List<String> postIds) {
        deletePosts_argumentMemberId = memberId;
        deletePosts_argumentPostsId = postIds;
    }

    @Override
    public void updatePost(String postId, UpdatePostRequest request, Long memberId) {
        updatePost_argumentMemberId = memberId;
        updatePost_argumentPostId = postId;
        updatePost_argumentRequest = request;
    }

    @Override
    public void increaseViews(String postId) {
        increaseViews_argumentPostId = postId;
    }

    @Override
    public void encrypt() {

    }

}
