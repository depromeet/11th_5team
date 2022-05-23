package depromeet.ohgzoo.iam.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OnePostsDto {
    private PostsDto postsDto;
    private boolean my;

    public OnePostsDto(PostsDto postsDto, boolean my) {
        this.postsDto = postsDto;
        this.my = my;
    }
}
