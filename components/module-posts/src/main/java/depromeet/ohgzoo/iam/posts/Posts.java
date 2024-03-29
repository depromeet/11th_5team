package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.BaseEntity;
import depromeet.ohgzoo.iam.DecryptConverter;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends BaseEntity {
    @Id
    @Column
    private String id;

    private Long memberId;

    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private FirstCategory firstCategory;

    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private SecondCategory secondCategory;

    @Convert(converter = DecryptConverter.class)
    private String content; // 글씨 제한?

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();

    private boolean disclosure;

    private int views;

    @Builder
    public Posts(String id, Long memberId, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, boolean disclosure, int views, LocalDateTime createdAt) {
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

    public Posts(String postId, Long memberId, FirstCategory firstCategory, SecondCategory secondCategory, String content,
                 List<String> tags, boolean disclosure) {
        this(postId, memberId, firstCategory, secondCategory, content, tags, disclosure, 0, LocalDateTime.now());
    }

    public void update(UpdatePostsRequest request) {
        this.secondCategory = request.getSecondCategory();
        this.content = request.getContent();
        this.tags = request.getTags();
        this.disclosure = request.getDisclosure();
    }

    public void increaseViews() {
        views++;
    }

    public boolean containsCategory(SecondCategory category) {
        return this.firstCategory.getCategoryId().equals(category.getCategoryId())
                || this.secondCategory.getCategoryId().equals(category.getCategoryId());
    }

    public void updateContent(String encrypt) {
        this.content = encrypt;
    }
}
