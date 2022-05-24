package depromeet.ohgzoo.iam.sharing;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SharingResponse {
    private String link;

    @Builder
    public SharingResponse(String link) {
        this.link = link;
    }
}
