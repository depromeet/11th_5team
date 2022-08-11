package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostExtensionApi {
    private final PostExtensionService postExtensionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostResult createPost(@Login Long memberId, @RequestBody CreatePostRequest request) {
        return postExtensionService.createPost(memberId, request);
    }

    @DeleteMapping
    public void deletePosts(@Login Long memberId, @RequestParam List<String> postIds) {
        postExtensionService.deletePosts(memberId, postIds);
    }

    @PatchMapping("/{postId}")
    public void updatePosts(
            @PathVariable("postId") String postId,
            @RequestBody UpdatePostRequest request,
            @Login Long memberId) {
        postExtensionService.updatePost(postId, request, memberId);
    }

    @PatchMapping("/{postid}/views")
    public void increaseViews(
            @PathVariable("postid") String postId) {
        postExtensionService.increaseViews(postId);
    }

    @GetMapping("/encrypt")
    public void encrypt() {
        postExtensionService.encrypt();
    }
}
