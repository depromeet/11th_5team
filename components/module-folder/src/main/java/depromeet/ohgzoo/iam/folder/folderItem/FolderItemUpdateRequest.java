package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FolderItemUpdateRequest {
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private Boolean disclosure;
    private Long folderId;

    public FolderItemUpdateRequest(SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure, Long folderId) {
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.folderId = folderId;
    }
}
