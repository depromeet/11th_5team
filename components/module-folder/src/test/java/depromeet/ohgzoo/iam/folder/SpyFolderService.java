package depromeet.ohgzoo.iam.folder;


import javax.persistence.EntityGraph;

public class SpyFolderService implements FolderService {
    public String createFolder_argumentName;
    public String argument_authToken;
    public FolderResponse createFolder_returnValue;
    public FolderResponse deleteFolder_returnValue;
    public Long deleteFolder_argumentId;
    public UpdateFolderRequest updateFolder_argumentRequest;
    public String updateFolder_argumentAuthToken;
    public Long updateFolder_argumentFolderId;
    public FolderResponse updateFolder_returnValue;

    @Override
    public FolderResponse createFolder(String authToken, String name) {
        createFolder_argumentName = name;
        argument_authToken = authToken;
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
