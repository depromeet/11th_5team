package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class FolderApi {
    private final FolderService folderService;

    @PostMapping("/api/v1/folders")
    public FolderResponse addFolder(@Login Long memberId, @Valid @RequestBody FolderCreateRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();

        FolderResponse response = folderService.createFolder(memberId, request);
        return response;
    }

    @DeleteMapping("/api/v1/folders/{folderId}")
    public void deleteFolder(@Login Long memberId,
                             @PathVariable Long folderId) {
        folderService.deleteFolder(memberId, folderId);
    }

    @PatchMapping("/api/v1/folders/{folderId}")
    public FolderResponse updateFolder(@Login Long memberId,
                                       @PathVariable Long folderId,
                                       @Valid @RequestBody FolderUpdateRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();
        return folderService.updateFolder(memberId, folderId, request);
    }

    @PostMapping("/api/v1/folders/posts/{folderId}")
    public void addFolderItem(@Login Long memberId, @PathVariable Long folderId, @Valid @RequestBody FolderItemCreateRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();
        folderService.createFolderItem(memberId, folderId, request);
    }

    @PatchMapping("/api/v1/folders/posts/{folderId}")
    public void moveFolderItem(@Login Long memberId, @PathVariable Long folderId, @Valid @RequestBody FolderItemMoveRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();
        folderService.moveFolderItem(memberId, folderId, request);
    }

    @GetMapping("/api/v1/folders")
    public FoldersGetResponse getFolders(@Login Long memberId) {
        return folderService.getFolders(memberId);
    }

    @GetMapping("/api/v1/folders/posts/{folderId}")
    public FolderItemsGetResponse getFolderItems(@Login Long memberId, @PathVariable Long folderId, @PageableDefault(size = 20) Pageable pageable) {
        return folderService.getFolderItems(memberId, folderId, pageable);
    }

    @DeleteMapping("/api/v1/folders/posts/{postId}")
    public void deleteFolderItem(@Login Long memberId, @PathVariable Long postId) {
        folderService.deleteFolderItem(memberId, postId);
    }
}