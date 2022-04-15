package depromeet.ohgzoo.iam.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FolderApi {

    private final FolderService folderService;

    @PostMapping("/api/v1/folder")
    public FolderResponse addFolder(@RequestHeader("AUTH_TOKEN") String authToken, @RequestParam String name) {
        if (name == null) throw new NullValueException();

        FolderResponse response = folderService.createFolder(authToken, name);
        return response;
    }

    @DeleteMapping("/api/v1/folder")
    public FolderResponse deleteFolder(@RequestHeader("AUTH_TOKEN") String authToken, @RequestParam String id) {
        if (id == null) throw new NullValueException();

        folderService.deleteFolder(authToken,id);
        return null;
    }

}
