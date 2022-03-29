package depromeet.ohgzoo.iam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApi {
    @GetMapping
    public String demo() {
        return "hello world";
    }
}
