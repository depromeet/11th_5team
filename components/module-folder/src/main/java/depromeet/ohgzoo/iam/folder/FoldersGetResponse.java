package depromeet.ohgzoo.iam.folder;

import lombok.Getter;

import java.util.List;

@Getter
public class FoldersGetResponse {
    private List<FolderGetResponse> folders;
    private List<String> postsThumbnail;

    public FoldersGetResponse(List<FolderGetResponse> folders, List<String> postsThumbnail) {
        this.folders = folders;
        this.postsThumbnail = postsThumbnail;
    }
}
