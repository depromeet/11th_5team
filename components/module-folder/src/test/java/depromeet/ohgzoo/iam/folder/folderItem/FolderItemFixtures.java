package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;

import static depromeet.ohgzoo.iam.folder.folderItem.FolderItem.FolderItemBuilder;

public class FolderItemFixtures {
    public static FolderItemBuilder aFolderItem() {

        return FolderItem.builder()
                .id(1L)
                .firstCategory(FirstCategory.SADNESS)
                .secondCategory(SecondCategory.SADNESS)
                .content("post content")
                .disclosure(false)
                .postId("1")
                .memberId(1L);
    }
}
