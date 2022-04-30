package depromeet.ohgzoo.iam.folder.folderItem;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderItemsGetResponse {
    private int totalCount;
    private List<FolderItemDto> posts;

    public FolderItemsGetResponse(int totalCount, List<FolderItemDto> posts) {
        this.totalCount = totalCount;
        this.posts = posts;
    }
}
