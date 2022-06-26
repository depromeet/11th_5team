package depromeet.ohgzoo.iam.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateMemberRequest {
    private String name;

    public UpdateMemberRequest(final String name) {
        this.name = name;
    }
}
