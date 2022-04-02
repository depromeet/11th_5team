package depromeet.ohgzoo.iam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DemoApi {
    private final DemoService demoService;

    @GetMapping
    public Demo demo() {
        return demoService.getDemo();
    }
}
