package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.folder.Folder;
import depromeet.ohgzoo.iam.folder.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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
    public void updateFolderItem(Long memberId, String postId, Folder folder, FolderItemUpdateRequest request) {
        FolderItem folderItem = folderItemRepository.findByPostId(postId)
                .orElseThrow(NotExistsFolderItemException::new);
        if (folderItem.getMemberId() != memberId) throw new InvalidUserException();
        if (folderItem.getFolder().getId() != request.getFolderId()) {
            moveFolderItem(memberId, folder, new FolderItemMoveRequest(folderItem.getPostId()));
        }
        folderItem.updateFolderItem(request);
    }

    @Override
    public void deleteFolderItems(Long memberId, List<String> postIds) {
        for (String postId : postIds) {
            FolderItem folderItem = folderItemRepository.findByPostId(postId).orElseThrow(NotExistsFolderItemException::new);
            folderItemRepository.delete(folderItem);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FolderItem> getRecentFolderItems(Long memberId) {
        List<FolderItem> folderItems = folderItemRepository.findTop4ByMemberIdOrderByCreatedAtDesc(memberId);
        return (folderItems == null || folderItems.isEmpty()) ? Collections.emptyList() : new ArrayList<>(folderItems);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FolderItem> getFolderItemsByFolder(Long memberId, Folder folder, Pageable pageable) {
        return folderItemRepository.findByFolderAndMemberIdOrderByCreatedAtDesc(folder, memberId, pageable);
    }

    @Override
    public void changeFolderCoverImage(Folder folder) {
        FolderItem folderItem = folderItemRepository.findFirstByFolderOrderByCreatedAtDesc(folder);
        folder.changeCoverImg((folderItem == null) ? FirstCategory.SADNESS : folderItem.getFirstCategory());
    }

    @Override
    public void increaseViews(String postId) {
        FolderItem folderItem = folderItemRepository.findByPostId(postId).orElseThrow(NotExistsFolderItemException::new);
        folderItem.increaseViews();
    }

    @Override
    public FolderItem getFolderItemByPostId(String postId) {
        return folderItemRepository.findByPostId(postId)
                .orElseThrow(NotExistsFolderItemException::new);
    }

    @Override
    public void deleteAllFolderItems(Long memberId) {
        folderItemRepository.deleteByMemberId(memberId);
    }
}
