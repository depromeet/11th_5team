package depromeet.ohgzoo.iam;

import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public Demo getDemo() {
        return new Demo("hello world");
    }
}
