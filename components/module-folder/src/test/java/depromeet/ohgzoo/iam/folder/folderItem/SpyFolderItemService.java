package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;

public class SpyFolderItemService implements FolderItemService {
    public Long argument_memberId;
    public Long createFolderItem_argumentFolderId;
    public Long deleteFolderItem_argumentPostId;
    public FolderItemMoveRequest moveFolderItem_argumentRequest;
    public FolderItemCreateRequest createFolderItem_argumentRequest;

    @Override
    public void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request) {
        argument_memberId = memberId;
        createFolderItem_argumentRequest = request;
        createFolderItem_argumentFolderId = folder.getId();
    }

    @Override
    public void moveFolderItem(Long memberId, Folder folder, FolderItemMoveRequest request) {
        argument_memberId = memberId;
        moveFolderItem_argumentRequest = request;
    }

    @Override
    public void deleteFolderItem(Long memberId, Long postId) {
        deleteFolderItem_argumentPostId = postId;
    }
}
