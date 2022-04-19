package depromeet.ohgzoo.iam.folder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderCreateRequest {
    private String folderName;

    public FolderCreateRequest(String folderName) {
        this.folderName = folderName;
    }
}
