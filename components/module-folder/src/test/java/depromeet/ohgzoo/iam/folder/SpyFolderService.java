package depromeet.ohgzoo.iam.folder;

public class SpyFolderService implements FolderService {
    public String argument_authToken;
    public FolderResponse createFolder_returnValue;
    public Long deleteFolder_argumentId;
    public UpdateFolderRequest updateFolder_argumentRequest;
    public FolderCreateRequest createFolder_argumentRequest;
    public String updateFolder_argumentAuthToken;
    public Long updateFolder_argumentFolderId;
    public FolderResponse updateFolder_returnValue;

    @Override
    public FolderResponse createFolder(String authToken, FolderCreateRequest request) {
        argument_authToken = authToken;
        createFolder_argumentRequest = request;
        return createFolder_returnValue;
    }

    @Override
    public void deleteFolder(String authToken, Long id) {
        deleteFolder_argumentId = id;
        argument_authToken = authToken;
    }

    @Override
    public FolderResponse updateFolder(String authToken, Long id, UpdateFolderRequest request) {
        updateFolder_argumentAuthToken = authToken;
        updateFolder_argumentFolderId = id;
        updateFolder_argumentRequest = request;
        return updateFolder_returnValue;
    }
}
