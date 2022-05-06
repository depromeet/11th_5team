package depromeet.ohgzoo.iam.postEvent;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostCreateEvent extends ApplicationEvent {
    private Long memberId;
    private FirstCategory firstCategory;
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private boolean disclosure;
    private Long folderId;
    private String postId;

    public PostCreateEvent(Object source,
                           Long memberId,
                           FirstCategory firstCategory,
                           SecondCategory secondCategory,
                           String content,
                           List<String> tags,
                           boolean disclosure,
                           Long folderId,
                           String postId
    ) {
        super(source);

        this.memberId = memberId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags.addAll(tags);
        this.disclosure = disclosure;
        this.folderId = folderId;
        this.postId = postId;
    }
}
