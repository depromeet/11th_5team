package depromeet.ohgzoo.iam.folder.folderItem;

public interface FolderItemService {
    void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request);

}
