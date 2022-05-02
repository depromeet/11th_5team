package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SpyFolderItemService implements FolderItemService {
    public Long argument_memberId;
    public Long createFolderItem_argumentFolderId;
    public Long deleteFolderItem_argumentPostId;
    public FolderItemMoveRequest moveFolderItem_argumentRequest;
    public FolderItemCreateRequest createFolderItem_argumentRequest;
    public List<FolderItem> getFolderItem_returnValue;


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
    public void changeFolderCoverImage(Folder folder) {
    }

    @Override
    public List<FolderItem> getRecentFolderItems(Long memberId) {
        argument_memberId = memberId;
        return getFolderItem_returnValue;
    }

    @Override
    public Page<FolderItem> getFolderItemsByFolder(Long memberId, Folder folder, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteFolderItem(Long memberId, Long postId) {
        deleteFolderItem_argumentPostId = postId;
    }
}
