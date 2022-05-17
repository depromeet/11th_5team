package depromeet.ohgzoo.iam.folder.folderItem;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderItemsGetResponse {
    private int totalCount;
    private String folderName;
    private List<FolderItemDto> posts;

    public FolderItemsGetResponse(int totalCount, String folderName, List<FolderItemDto> posts) {
        this.totalCount = totalCount;
        this.folderName = folderName;
        this.posts = posts;
    }
}
