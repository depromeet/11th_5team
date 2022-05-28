package depromeet.ohgzoo.iam.postEvent;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostUpdateEvent extends ApplicationEvent {
    private Long memberId;
    private String postId;
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private boolean disclosure;
    private Long folderId;

    public PostUpdateEvent(Object source, Long memberId, String postId, SecondCategory secondCategory, String content, List<String> tags, boolean disclosure, Long folderId) {
        super(source);
        this.memberId = memberId;
        this.postId = postId;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.folderId = folderId;
    }
}
