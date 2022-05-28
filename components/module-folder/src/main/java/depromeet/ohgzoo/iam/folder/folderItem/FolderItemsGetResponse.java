package depromeet.ohgzoo.iam.folder.folderItem;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderItemsGetResponse {
    private int totalCount;
    private String folderName;
    private boolean isDefault;
    private List<FolderItemDto> posts;

    public FolderItemsGetResponse(int totalCount, String folderName, boolean isDefault, List<FolderItemDto> posts) {
        this.totalCount = totalCount;
        this.folderName = folderName;
        this.isDefault = isDefault;
        this.posts = posts;
    }
}
