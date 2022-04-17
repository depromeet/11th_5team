package depromeet.ohgzoo.iam.folder;

import lombok.Getter;

@Getter
public class FolderCreateRequest {
    private String folderName;

    public FolderCreateRequest(String folderName) {
        this.folderName = folderName;
    }
}
