package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;

import java.time.LocalDateTime;
import java.util.List;

public class PostFixtures {
    public static Posts.PostsBuilder aPost() {
        return Posts.builder()
                .id("id")
                .memberId(1L)
                .firstCategory(FirstCategory.SADNESS)
                .secondCategory(SecondCategory.SADNESS)
                .content("content")
                .tags(List.of("tag1"))
                .disclosure(true)
                .views(0)
                .createdAt(LocalDateTime.now());
    }
}
