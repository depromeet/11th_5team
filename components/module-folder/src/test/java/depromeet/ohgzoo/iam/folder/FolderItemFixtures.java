package depromeet.ohgzoo.iam.folder;

import static depromeet.ohgzoo.iam.folder.FolderItem.FolderItemBuilder;

public class FolderItemFixtures {
    public static FolderItemBuilder aFolderItem(){
        return FolderItem.builder()
                .id(1L)
                .firstCategory(FirstCategory.ANGRY)
                .secondCategory(SecondCategory.UPSET)
                .content("post content")
                .disclosure(false);
    }
}
