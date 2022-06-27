package depromeet.ohgzoo.iam.folderEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FolderItemDeleteEvent extends ApplicationEvent {
    private Long memberId;
    private List<String> postIds = new ArrayList<>();

    public FolderItemDeleteEvent(Object source, Long memberId, List<String> postIds) {
        super(source);
        this.memberId = memberId;
        this.postIds = postIds;
    }
}
