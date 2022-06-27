package depromeet.ohgzoo.iam.folder.folderItem;

import org.springframework.context.ApplicationEventPublisher;

public class SpyApplicationEventPublisher implements ApplicationEventPublisher {
    public Object publicEvent_argumentEvent;

    @Override
    public void publishEvent(Object event) {
        publicEvent_argumentEvent = event;
    }
}
