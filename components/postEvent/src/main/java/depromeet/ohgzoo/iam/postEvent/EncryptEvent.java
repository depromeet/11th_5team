package depromeet.ohgzoo.iam.postEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EncryptEvent extends ApplicationEvent {
    public EncryptEvent(Object source) {
        super(source);
    }
}
