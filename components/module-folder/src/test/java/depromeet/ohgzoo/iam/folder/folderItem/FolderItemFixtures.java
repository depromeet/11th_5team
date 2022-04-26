package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.FirstCategory;
import depromeet.ohgzoo.iam.folder.SecondCategory;

import static depromeet.ohgzoo.iam.folder.folderItem.FolderItem.FolderItemBuilder;

public class FolderItemFixtures {
    public static FolderItemBuilder aFolderItem() {

        return FolderItem.builder()
                .id(1L)
                .firstCategory(FirstCategory.ANGRY)
                .secondCategory(SecondCategory.UPSET)
                .content("post content")
                .disclosure(false)
                .postId(1L);
    }
}
