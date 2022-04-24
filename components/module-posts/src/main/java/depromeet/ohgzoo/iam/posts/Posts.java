package depromeet.ohgzoo.iam.posts;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {
    @Id @GeneratedValue
    @Column
    private Long id;

    private Long memberId;

    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private PostsFirstCategory firstCategory;

    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private PostsSecondCategory secondCategory;

    private String content; // 글씨 제한?

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();

    private boolean disclosure;

    private int views;

    private LocalDateTime createdAt;

    @Builder
    public Posts(Long id, Long memberId, PostsFirstCategory firstCategory, PostsSecondCategory secondCategory, String content, List<String> tags, boolean disclosure, int views, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.views = views;
        this.createdAt = createdAt;
    }

    public Posts(Long memberId, PostsFirstCategory firstCategory, PostsSecondCategory secondCategory, String content,
                 List<String> tags, boolean disclosure) {
        this(null, memberId, firstCategory, secondCategory, content, tags, disclosure, 0, LocalDateTime.now());
    }

    public void addViewNum() {
        this.views += 1;
    }
}
