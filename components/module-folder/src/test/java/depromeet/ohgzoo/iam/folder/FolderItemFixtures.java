package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;

import static depromeet.ohgzoo.iam.folder.folderItem.FolderItem.FolderItemBuilder;

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
