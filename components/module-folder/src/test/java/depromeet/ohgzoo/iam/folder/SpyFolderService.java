package depromeet.ohgzoo.iam.folder;


public class SpyFolderService implements FolderService {
    public String createFolder_argumentName;
    public String argument_authToken;
    public CreateFolderResponse createFolder_returnValue;

    @Override
    public CreateFolderResponse createFolder(String authToken, String name) {
        createFolder_argumentName = name;
        argument_authToken = authToken;
        return createFolder_returnValue;
    }
}
