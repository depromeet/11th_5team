package depromeet.ohgzoo.iam.folder;

import lombok.Getter;

@Getter
public class FolderResponse {
    private Long folderId;
    private String folderName;

    public FolderResponse(Long folderId, String folderName) {
        this.folderId = folderId;
        this.folderName = folderName;
    }


}