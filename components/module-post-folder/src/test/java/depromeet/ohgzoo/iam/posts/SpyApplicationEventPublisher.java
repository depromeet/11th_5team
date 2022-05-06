package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import org.springframework.context.ApplicationEventPublisher;

public class SpyApplicationEventPublisher implements ApplicationEventPublisher {
    public PostCreateEvent publishEvent_argumentEvent;

    @Override
    public void publishEvent(Object event) {
        publishEvent_argumentEvent = (PostCreateEvent) event;
    }
}
