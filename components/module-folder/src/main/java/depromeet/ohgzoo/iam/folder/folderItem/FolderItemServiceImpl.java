package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.folder.Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderItemServiceImpl implements FolderItemService {

    private final FolderItemRepository folderItemRepository;

    @Override
    public void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request) {
        FolderItem folderItem = new FolderItem(request.getFirstCategory(), request.getSecondCategory(), request.getContent(), request.getTags(), request.getDisclosure(), request.getPostId(), memberId);
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
    public void changeFolderCoverImage(Folder folder) {
        FolderItem folderItem = folderItemRepository.findFirstByFolderOrderByCreatedAtDesc(folder);
        folder.changeCoverImg((folderItem == null) ? FirstCategory.DEFAULT : folderItem.getFirstCategory());
    }

    @Override
    public List<FolderItem> getRecentFolderItems(Long memberId) {
        List<FolderItem> folderItems = folderItemRepository.findTop4ByMemberIdOrderByCreatedAtDesc(memberId);
        return (folderItems == null || folderItems.isEmpty()) ? Collections.emptyList() : new ArrayList<>(folderItems);
    }
}
