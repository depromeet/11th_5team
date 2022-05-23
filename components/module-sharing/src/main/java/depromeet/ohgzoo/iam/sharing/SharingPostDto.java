package depromeet.ohgzoo.iam.sharing;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SharingPostDto {
    private String receiverName;
    private SharingCategory category;
    private String content;
    private String senderName;

    @Builder
    public SharingPostDto(String receiverName, SharingCategory category, String content, String senderName) {
        this.receiverName = receiverName;
        this.category = category;
        this.content = content;
        this.senderName = senderName;
    }
}
