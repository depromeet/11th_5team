package depromeet.ohgzoo.iam.search.batch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "posts", url = "http://localhost:8080")
public interface PostsClient {
    @GetMapping("/api/v1/posts/all")
    ResultModel<List<RemotePosts>> getPosts();
}
