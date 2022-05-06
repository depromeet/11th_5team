package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.jwt.Login;
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
public class PostExtensionApi {
    private final PostExtensionService postExtensionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@Login Long memberId, @RequestBody CreatePostRequest request) {
        postExtensionService.createPost(memberId, request);
    }
}
