package depromeet.ohgzoo.iam.member;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberCreateEvent extends ApplicationEvent {
    private Long memberId;

    public MemberCreateEvent(Object source, Long memberId) {
        super(source);
        this.memberId = memberId;
    }
}
