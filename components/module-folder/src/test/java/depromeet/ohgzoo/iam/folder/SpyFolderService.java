package depromeet.ohgzoo.iam.folder;


public class SpyFolderService implements FolderService {
    public String createFolder_argumentName;
    public String argument_authToken;
    public FolderResponse createFolder_returnValue;
    public FolderResponse deleteFolder_returnValue;
    public String deleteFolder_argumentId;

    @Override
    public FolderResponse createFolder(String authToken, String name) {
        createFolder_argumentName = name;
        argument_authToken = authToken;
        return createFolder_returnValue;
    }

    @Override
    public void deleteFolder(String authToken, String id) {
        deleteFolder_argumentId = id;
        argument_authToken = authToken;
    }
}
