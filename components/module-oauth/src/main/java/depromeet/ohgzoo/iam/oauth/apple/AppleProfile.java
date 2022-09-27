package depromeet.ohgzoo.iam.oauth.apple;

import lombok.Getter;

@Getter
public class AppleProfile {
    private String id;

    public AppleProfile(final String id) {
        this.id = id;
    }
}
