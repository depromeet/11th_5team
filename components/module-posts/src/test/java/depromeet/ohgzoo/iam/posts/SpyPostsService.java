package depromeet.ohgzoo.iam.posts;

public class SpyPostsService implements PostsService {
    public CreatePostsRequest createPosts_argumentRequest;
    public CreatePostsResult createPosts_returnValue;

    @Override
    public CreatePostsResult createPosts(CreatePostsRequest request) {
        createPosts_argumentRequest = request;
        return createPosts_returnValue;
    }
}
