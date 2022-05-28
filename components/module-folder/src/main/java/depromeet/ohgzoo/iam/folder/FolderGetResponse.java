package depromeet.ohgzoo.iam.folder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FolderGetResponse {

    private Long folderId;
    private boolean isDefault;
    private String folderName;
    private String coverImg;
    private int postCount;

    public static FolderGetResponse of(Folder folder) {
        return new FolderGetResponse(
                folder.getId(),
                folder.isDefault(),
                folder.getName(),
                folder.getCoverImg(),
                folder.getFolderItems().size()
        );
    }
}
