package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
@RestController
public class FolderApi {
    private final FolderService folderService;

    @PostMapping
    public FolderResponse addFolder(@Login Long memberId, @Valid @RequestBody FolderCreateRequest request) {
        FolderResponse response = folderService.createFolder(memberId, request);
        return response;
    }

    @DeleteMapping("{folderId}")
    public void deleteFolder(@Login Long memberId,
                             @PathVariable Long folderId) {
        folderService.deleteFolder(memberId, folderId);
    }

    @PatchMapping("{folderId}")
    public FolderResponse updateFolder(@Login Long memberId,
                                       @PathVariable Long folderId,
                                       @Valid @RequestBody FolderUpdateRequest request) {
        return folderService.updateFolder(memberId, folderId, request);
    }

    @PostMapping("{folderId}/posts")
    public void addFolderItem(@Login Long memberId, @PathVariable Long folderId,
                              @Valid @RequestBody FolderItemCreateRequest request) {
        folderService.createFolderItem(memberId, folderId, request);
    }

    @PatchMapping("{folderId}/posts")
    public void moveFolderItem(@Login Long memberId, @PathVariable Long folderId,
                               @Valid @RequestBody FolderItemMoveRequest request) {
        folderService.moveFolderItem(memberId, folderId, request);
    }

    @DeleteMapping("{folderId}/posts")
    public void deleteAllFolderItems(@Login Long memberId, @PathVariable Long folderId) {
        folderService.deleteAllFolderItems(memberId, folderId);
    }

    @GetMapping
    public FoldersGetResponse getFolders(@Login Long memberId) {
        return folderService.getFolders(memberId);
    }

    @GetMapping("{folderId}/posts")
    public FolderItemsGetResponse getFolderItems(@Login Long memberId, @PathVariable Long folderId,
                                                 @PageableDefault(size = 20) Pageable pageable) {
        return folderService.getFolderItems(memberId, folderId, pageable);
    }

    @GetMapping("posts/{postId}")
    public FolderGetResponse getFolderByPost(@PathVariable String postId) {
        return folderService.getFolderByPost(postId);
    }
}