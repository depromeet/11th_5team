package depromeet.ohgzoo.iam.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApi {

    private final PostsService postsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreatePostsResult createPosts(@RequestBody CreatePostsRequest request) {
        return postsService.createPosts(request);
    }
}
