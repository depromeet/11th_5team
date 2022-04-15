package depromeet.ohgzoo.iam.folder;

import lombok.Getter;

@Getter
public class CreateFolderResponse {
    private Long folderId;
    private String folderName;

    public CreateFolderResponse(Long folderId,String folderName){
        this.folderId=folderId;
        this.folderName=folderName;
    }
}