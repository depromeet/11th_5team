package depromeet.ohgzoo.iam.folder;

public class SpyFolderService implements FolderService {
    public Long argument_memberId;
    public FolderResponse createFolder_returnValue;
    public Long deleteFolder_argumentId;
    public FolderUpdateRequest updateFolder_argumentRequest;
    public FolderCreateRequest createFolder_argumentRequest;
    public FolderItemCreateRequest createFolderItem_argumentRequest;
    public Long updateFolder_argumentMemberId;
    public Long updateFolder_argumentFolderId;
    public Long createFolderItem_argumentFolderId;
    public FolderResponse updateFolder_returnValue;


    @Override
    public FolderResponse createFolder(Long memberId, FolderCreateRequest request) {
        argument_memberId = memberId;
        createFolder_argumentRequest = request;
        return createFolder_returnValue;
    }

    @Override
    public void deleteFolder(Long memberId, Long id) {
        deleteFolder_argumentId = id;
        argument_memberId = memberId;
    }

    @Override
    public FolderResponse updateFolder(Long memberId, Long id, FolderUpdateRequest request) {
     updateFolder_argumentMemberId=memberId;
        updateFolder_argumentFolderId = id;
        updateFolder_argumentRequest = request;
        return updateFolder_returnValue;
    }

    @Override
    public void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request){
        argument_memberId = memberId;
        createFolderItem_argumentRequest=request;
        createFolderItem_argumentFolderId=memberId;
    }
}
