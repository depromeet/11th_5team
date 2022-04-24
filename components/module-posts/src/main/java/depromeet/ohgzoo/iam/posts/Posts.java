package depromeet.ohgzoo.iam.posts;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private PostsFirstCategory firstCategory;

    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private PostsSecondCategory secondCategory;

    private String content; // 글씨 제한?

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();

    private Boolean disclosure; // 기본 설정?

    private Integer views;

    private LocalDateTime createdAt;

    @Builder
    public Posts(PostsFirstCategory firstCategory, PostsSecondCategory secondCategory, String content,
                 List<String> tags, Boolean disclosure) {
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.views = 0; // @Column(columnDefinition = "integer default 0") ?
        this.createdAt = LocalDateTime.now();
    }

    public void addViewNum() {
        this.views += 1;
    }

    public void update(UpdatePostsRequest request) {
        this.secondCategory = request.getSecondCategory();
        this.content = request.getContent();
        this.tags = request.getTags();
        this.disclosure = request.getDisclosure();
    }
}
