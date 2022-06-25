package depromeet.ohgzoo.iam.member;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberDeleteEvent extends ApplicationEvent {

    private Long memberId;

    public MemberDeleteEvent(Object source, Long memberId) {
        super(source);
        this.memberId = memberId;
    }
}