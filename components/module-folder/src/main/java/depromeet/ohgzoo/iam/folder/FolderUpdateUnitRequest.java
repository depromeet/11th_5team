package depromeet.ohgzoo.iam.folder;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderUpdateUnitRequest {
    private Long folderId;
    private String folderName;

    public FolderUpdateUnitRequest(Long folderId, String folderName) {
        this.folderId = folderId;
        this.folderName = folderName;
    }
}
