package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.Folder.FolderBuilder;

public class FolderFixtures {
    public static FolderBuilder aFolder() {
        return Folder.builder()
                .id(1L)
                .name("folder name")
                .memberId(1L)
                .coverImg("cover image");
    }
}
