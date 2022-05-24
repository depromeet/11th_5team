package depromeet.ohgzoo.iam.sharing;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SharingRequest {
    private String receiverName;
    private SharingCategory category;
    private String postId;

    @Builder
    public SharingRequest(String receiverName, SharingCategory category, String postId) {
        this.receiverName = receiverName;
        this.category = category;
        this.postId = postId;
    }
}
