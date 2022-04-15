package depromeet.ohgzoo.iam.folder;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FolderApi {

    private final FolderService folderService;

    @PostMapping("/api/v1/folder")
    public CreateFolderResponse addFolder(@RequestHeader("AUTH_TOKEN") String authToken, @RequestParam String name) {
        if (name == null) throw new NullValueException();

        CreateFolderResponse response = folderService.createFolder(authToken, name);
        return response;
    }

}
