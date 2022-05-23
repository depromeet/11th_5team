package depromeet.ohgzoo.iam.postEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IncreaseViewEvent extends ApplicationEvent {
    private String postId;

    public IncreaseViewEvent(Object source, String postId) {
        super(source);
        this.postId = postId;
    }
}
