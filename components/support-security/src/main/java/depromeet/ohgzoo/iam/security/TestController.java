package depromeet.ohgzoo.iam.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/{postid}")
    public String test(@PostEntity Post post, @Login Long userId) {
        return "ok";
    }
}
