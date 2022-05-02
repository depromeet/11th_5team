package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.FirstCategory;
import depromeet.ohgzoo.iam.folder.Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderItemServiceImpl implements FolderItemService {

    private final FolderItemRepository folderItemRepository;

    @Override
    public void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request) {
        FolderItem folderItem = new FolderItem(request.getFirstCategory(), request.getSecondCategory(), request.getContent(), request.getTags(), request.getDisclosure(), request.getPostId());
        folderItemRepository.save(folderItem);

        folderItem.setFolder(folder);
        folder.changeCoverImg(folderItem.getFirstCategory());
    }

    @Override
    public void moveFolderItem(Long memberId, Folder folder, FolderItemMoveRequest request) {
        // 폴더 이동
        FolderItem folderItem = folderItemRepository.findByPostId(request.getFolderItemId())
                .orElseThrow(NotExistsFolderItemException::new);

        Folder oldFolder = folderItem.getFolder();
        folderItem.changeFolder(oldFolder, folder);

        // coverImg 갱신
        changeFolderCoverImage(oldFolder);
        changeFolderCoverImage(folder);
    }

    @Override
    public void deleteFolderItem(Long memberId, Long postId) {
        FolderItem folderItem = folderItemRepository.findByPostId(postId)
                .orElseThrow(NotExistsFolderItemException::new);

        folderItem.unsetFolder();
        folderItemRepository.delete(folderItem);
    }

    private void changeFolderCoverImage(Folder folder) {
        FolderItem folderItem = folderItemRepository.findFirstByFolderOrderByCreatedAtDesc(folder);
        folder.changeCoverImg((folderItem == null) ? FirstCategory.DEFAULT : folderItem.getFirstCategory());
    }
}
