package depromeet.ohgzoo.iam.postEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDeleteEvent extends ApplicationEvent {
    private Long memberId;
    private List<String> postIds = new ArrayList<>();

    public PostDeleteEvent(Object source, Long memberId, List<String> postIds) {
        super(source);
        this.memberId = memberId;
        this.postIds.addAll(postIds);
    }
}