package depromeet.ohgzoo.iam.oauth.apple;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppleHeader {
    private String kid;
    private String alg;
}
