package depromeet.ohgzoo.iam;

public class SpyDemoService implements DemoService {
    public Demo getDemo_returnValue;

    @Override
    public Demo getDemo() {
        return getDemo_returnValue;
    }
}
