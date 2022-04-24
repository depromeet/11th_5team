package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemService;

public class SpyFolderItemService implements FolderItemService {
    public Long argument_memberId;
    public FolderItemCreateRequest createFolderItem_argumentRequest;
    public Long createFolderItem_argumentFolderId;
    @Override
    public void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request) {
        argument_memberId = memberId;
        createFolderItem_argumentRequest = request;
        createFolderItem_argumentFolderId = memberId;
    }
}
