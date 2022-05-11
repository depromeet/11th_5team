package depromeet.ohgzoo.iam.member;

import org.springframework.context.ApplicationEventPublisher;

public class SpyApplicationEventPublisher implements ApplicationEventPublisher {
    public Object publishEvent_argumentEvent;

    @Override
    public void publishEvent(Object event) {
        publishEvent_argumentEvent = event;
    }
}
