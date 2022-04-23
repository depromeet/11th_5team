package depromeet.ohgzoo.iam.posts;

import java.util.List;

public class SpyPostsService implements PostsService {
    public CreatePostsRequest createPosts_argumentRequest;
    public CreatePostsResult createPosts_returnValue;
    public UpdatePostsRequest updatePostsRequest_argumentRequest;

    @Override
    public CreatePostsResult createPosts(CreatePostsRequest request) {
        createPosts_argumentRequest = request;
        return createPosts_returnValue;
    }

    @Override
    public void updatePosts(Long postId, UpdatePostsRequest request, Long memberId) {
        updatePostsRequest_argumentRequest = request;
    }

    @Override
    public void deletePosts(List<Long> postIds, Long memberId) {

    }
}