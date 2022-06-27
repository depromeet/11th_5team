package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.ImageLoader;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemDeleteEvent;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemDto;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemService;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemUpdateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderItemService folderItemService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public FolderResponse createFolder(Long memberId, FolderCreateRequest request) {
        Optional<Folder> existedFolder = folderRepository.findByMemberIdAndName(memberId, request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();

        Folder folder = new Folder(request.getFolderName(), "", memberId);
        folderRepository.save(folder);

        return new FolderResponse(folder.getId(), folder.getName());
    }

    @Override
    public void deleteFolder(Long memberId, Long folderId) {

        Folder folder = folderRepository.findById(folderId).orElseThrow(NotExistsFolderException::new);
        if (folder.getMemberId() != memberId) throw new InvalidUserException();
        if (folder.isDefault() == true) throw new ProtectedFolderException();

        Folder defaultFolder = folderRepository.findByMemberIdAndIsDefaultTrue(memberId);
        for (FolderItem folderItem : folder.getFolderItems()) {
            folderItem.setFolder(defaultFolder);
        }

        folderRepository.deleteById(folderId);
    }

    @Override
    public FolderResponse updateFolder(Long memberId, Long folderId, FolderUpdateRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        Optional<Folder> existedFolder = folderRepository.findByMemberIdAndName(memberId, request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();
        if (folder.getMemberId() != memberId) throw new InvalidUserException();

        folder.updateName(request.getFolderName());
        return new FolderResponse(folder.getId(), folder.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public FoldersGetResponse getFolders(Long memberId) {
        List<Folder> folders = folderRepository.findAllByMemberId(memberId);
        List<FolderItem> folderItems = folderItemService.getRecentFolderItems(memberId);

        List<String> coverImages = getCoverImages(folderItems);

        return new FoldersGetResponse(folders.stream()
                .map(FolderGetResponse::of)
                .collect(Collectors.toList()), coverImages);
    }

    @Override
    public void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request) {
        Folder folder = getFolderOrDefault(memberId, folderId);

        folderItemService.createFolderItem(memberId, folder, request);
    }

    private Folder getFolderOrDefault(Long memberId, Long folderId) {
        if (null == folderId) {
            return folderRepository.findByMemberIdAndIsDefaultTrue(memberId);
        }
        return folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);
    }

    @Override
    public void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request) {
        Folder newFolder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        folderItemService.moveFolderItem(memberId, newFolder, request);
    }

    @Override
    public void updateFolderItem(Long memberId, String postId, FolderItemUpdateRequest request) {
        Folder newFolder = folderRepository.findById(request.getFolderId())
                .orElseThrow(NotExistsFolderException::new);

        folderItemService.updateFolderItem(memberId, postId, newFolder, request);
    }

    @Override
    public FolderGetResponse getFolderByPost(String postId) {
        FolderItem folderItem = folderItemService.getFolderItemByPostId(postId);
        return FolderGetResponse.of(folderItem.getFolder());
    }

    @Override
    public void deleteAllFolders(Long memberId) {
        folderItemService.deleteAllFolderItems(memberId);
        folderRepository.deleteByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public FolderItemsGetResponse getFolderItems(Long memberId, Long folderId, Pageable pageable) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        List<FolderItem> folderItems = folder.getFolderItems()
                .stream()
                .sorted(Comparator.comparing(FolderItem::getCreatedAt).reversed())
                .skip(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new FolderItemsGetResponse(folder.getFolderItems().size(), folder.getName(), folder.isDefault(), folderItems.stream()
                .map(FolderItemDto::of).collect(Collectors.toList()));
    }

    @Override
    public void deleteFolderItems(Long memberId, List<String> postIds) {
        folderItemService.deleteFolderItems(memberId, postIds);
    }

    @Override
    public void deleteAllFolderItems(Long memberId, Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        List<String> postIds = folder.getFolderItems()
                .stream()
                .map(folderItem -> folderItem.getPostId())
                .collect(Collectors.toList());

        folderItemService.deleteFolderItems(memberId, postIds);
        eventPublisher.publishEvent(new FolderItemDeleteEvent(this, memberId, postIds));
    }

    @Override
    public void createDefaultFolder(Long memberId) {
        Folder folder = new Folder("미분류", ImageLoader.DEFAULT_IMAGE, memberId, true);
        folderRepository.save(folder);
    }

    @Override
    public void increaseViews(String postId) {
        folderItemService.increaseViews(postId);
    }

    private List<String> getCoverImages(List<FolderItem> folderItems) {
        List<String> coverImages = new ArrayList<>();
        for (int i = 0; i < folderItems.size(); i++) {
            if (folderItems.get(i) != null)
                coverImages.add(folderItems.get(i).getFirstCategory().getImage());
        }
        return coverImages;
    }
}