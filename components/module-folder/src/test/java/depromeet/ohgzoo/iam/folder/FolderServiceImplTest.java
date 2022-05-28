package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemService;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemServiceImpl;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemUpdateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import depromeet.ohgzoo.iam.folder.folderItem.NotExistsFolderItemException;
import depromeet.ohgzoo.iam.folder.folderItem.SpyFolderItemRepository;
import depromeet.ohgzoo.iam.member.Member;
import depromeet.ohgzoo.iam.member.SpyMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static depromeet.ohgzoo.iam.folder.FolderFixtures.aFolder;
import static depromeet.ohgzoo.iam.folder.folderItem.FolderItemFixtures.aFolderItem;
import static org.assertj.core.api.Assertions.assertThat;

public class FolderServiceImplTest {

    private FolderService folderService;
    private FolderItemService folderItemService;
    private SpyFolderRepository spyFolderRepository;
    private SpyFolderItemRepository spyFolderItemRepository;
    private SpyMemberRepository spyMemberRepository;

    @BeforeEach
    void setUp() {
        spyFolderRepository = new SpyFolderRepository();
        spyMemberRepository = new SpyMemberRepository();
        spyFolderItemRepository = new SpyFolderItemRepository();
        folderItemService = new FolderItemServiceImpl(spyFolderItemRepository);
        folderService = new FolderServiceImpl(spyFolderRepository, folderItemService);

        spyMemberRepository.findById_returnValue = Member.builder().build();
    }

    @Test
    void createFolder_callsSaveInFolderRepository() {
        folderService.createFolder(1L, new FolderCreateRequest("folderName"));

        Folder savedFolder = spyFolderRepository.save_argumentFolder;
        assertThat(savedFolder.getId()).isNull();
        assertThat(savedFolder.getName()).isEqualTo("folderName");
        assertThat(savedFolder.getCoverImg()).isEqualTo("");
        assertThat(savedFolder.getMemberId()).isEqualTo(1L);
    }

    @Test
    void createFolder_returnsFolderResponse() {
        spyFolderRepository.save_returnValue = aFolder()
                .name("givenFolderName")
                .build();

        FolderResponse result = folderService.createFolder(1L, new FolderCreateRequest("givenFolderName"));

        assertThat(result.getFolderId()).isEqualTo(null);
        assertThat(result.getFolderName()).isEqualTo("givenFolderName");
    }

    @Test
    void createDefaultFolder_callsSaveInFolderRepository() {
        folderService.createDefaultFolder(1L);

        Folder savedFolder = spyFolderRepository.save_argumentFolder;
        assertThat(savedFolder.getName()).isEqualTo("미분류");
        assertThat(savedFolder.isDefault()).isEqualTo(true);
        assertThat(savedFolder.getMemberId()).isEqualTo(1L);
    }

    @Test
    void deleteFolder_callsDeleteFromRepository() {
        folderService.deleteFolder(1L, 1L);

        assertThat(spyFolderRepository.delete_argumentFolderId).isNotNull();
    }

    @Test
    void deleteFolder_throwsException_whenMemberIdIsNotEqualsFolder() {
        spyFolderRepository.findById_returnValue = aFolder()
                .memberId(1L)
                .build();

        Assertions.assertThatThrownBy(() -> folderService.deleteFolder(2L, 1L))
                .isInstanceOf(InvalidUserException.class);
    }

    @Test
    void deleteFolder_throwsException_whenFolderIsDefaultFolder() {
        spyFolderRepository.findById_returnValue = aFolder()
                .memberId(1L)
                .id(1L)
                .name("미분류")
                .isDefault(true)
                .build();

        Assertions.assertThatThrownBy(() -> folderService.deleteFolder(1L, 1L))
                .isInstanceOf(ProtectedFolderException.class);
    }

    @Test
    void deleteFolder_changesFolderItemToDefaultFolder() {
        FolderItem folderItem = aFolderItem().build();
        Folder defaultFolder = aFolder().build();
        Folder folder = aFolder().id(2L).build();
        folderItem.setFolder(folder);

        spyFolderRepository.findById_returnValue = folder;
        spyFolderRepository.findByIsDefaultTrue_returnValue = defaultFolder;
        folderService.deleteFolder(1L, 2L);
    }


    @Test
    void updateFolder_passesFolderIdToRepository() {

        folderService.updateFolder(1L, 1L, new FolderUpdateRequest("1234"));

        assertThat(spyFolderRepository.findById_argumentId).isEqualTo(1L);
    }

    @Test
    void updateFolder_throwsNotExistsFolderException_whenFolderIsNotPresent() {
        spyFolderRepository.findById_returnValue = null;

        Assertions.assertThatThrownBy(() -> folderService.updateFolder(1L, 1L, null))
                .isInstanceOf(NotExistsFolderException.class);
    }

    @Test
    void updateFolder_throwsException_whenMemberIdIsNotEqualsFolder() {
        spyFolderRepository.save_returnValue = aFolder()
                .memberId(1L)
                .build();

        Assertions.assertThatThrownBy(() -> folderService.updateFolder(2L, 1L, new FolderUpdateRequest("newFolderName")))
                .isInstanceOf(InvalidUserException.class);
    }

    @Test
    void updateFolder_updateFolderName() {
        Folder givenFolder = aFolder()
                .id(1L)
                .name("oldName")
                .build();
        spyFolderRepository.findById_returnValue = givenFolder;

        folderService.updateFolder(1L, 1L, new FolderUpdateRequest("givenNewName"));

        assertThat(givenFolder.getName()).isEqualTo("givenNewName");
    }

    @Test
    void updateFolder_returnsFolderResponse() {
        Folder givenFolder = aFolder()
                .name("oldName")
                .build();

        spyFolderRepository.findById_returnValue = givenFolder;
        FolderResponse result = folderService.updateFolder(1L, 1L, new FolderUpdateRequest("givenNewName"));

        assertThat(result.getFolderId()).isEqualTo(1L);
        assertThat(result.getFolderName()).isEqualTo("givenNewName");
    }

    @Test
    void updateFolder_throwsException_whenRequestNameIsDuplicated() {
        Folder existedFolder = aFolder()
                .name("folder")
                .build();

        spyFolderRepository.findByName_returnValue = existedFolder;

        Assertions.assertThatThrownBy(() -> folderService.updateFolder(1L, 1L, new FolderUpdateRequest("existedFolder")))
                .isInstanceOf(ExistedNameException.class);
    }

    @Test
    void createFolderItem_callsSaveInFolderItemRepository() {
        folderItemService.createFolderItem(1L, aFolder().build(), new FolderItemCreateRequest("1", FirstCategory.SADNESS, SecondCategory.ANXIOUS, "post content", null, false));

        FolderItem savedFolderItem = spyFolderItemRepository.save_argumentFolderItem;
        assertThat(savedFolderItem.getId()).isNull();
        assertThat(savedFolderItem.getContent()).isEqualTo("post content");
    }

    @Test
    void createFolderItem_callsAddInFolderRepository() {
        Folder existedFolder = aFolder()
                .id(1L)
                .build();
        spyFolderRepository.findById_returnValue = existedFolder;
        folderItemService.createFolderItem(1L, existedFolder, new FolderItemCreateRequest("1", FirstCategory.SADNESS, SecondCategory.ANXIOUS, "post content", null, false));

        assertThat(existedFolder.getFolderItems().get(0).getContent()).isEqualTo("post content");
    }

    @Test
    void createFolderItem_changersFolderCoverImg() {
        Folder existedFolder = aFolder()
                .id(1L)
                .build();
        spyFolderRepository.findById_returnValue = existedFolder;

        FirstCategory givenCategory = FirstCategory.SADNESS;
        folderItemService.createFolderItem(1L, existedFolder, new FolderItemCreateRequest("1", givenCategory, SecondCategory.ANXIOUS, "post content", null, false));

        assertThat(existedFolder.getCoverImg()).isEqualTo(givenCategory.getImage());
    }

    @Test
    void moveFolderItem_MovesFolderItemNewFolder() {
        FolderItem folderItem = aFolderItem()
                .id(1L)
                .build();
        Folder oldFolder = aFolder().id(1L).build();
        folderItem.setFolder(oldFolder);
        Folder newFolder = aFolder().id(2L).build();
        spyFolderItemRepository.findByPostId_returnValue = folderItem;
        spyFolderRepository.findById_returnValue = newFolder;
        spyFolderItemRepository.latestFolderItem_returnValue = folderItem;
        folderItemService.moveFolderItem(1L, newFolder, new FolderItemMoveRequest("1"));

        assertThat(folderItem.getFolder().getId()).isEqualTo(2L);
    }

    @Test
    void moveFolderItem_ChangesFolderCoverImage() {
        FolderItem folderItem1 = aFolderItem().id(2L).firstCategory(FirstCategory.SADNESS).build();
        FirstCategory givenCategory = FirstCategory.ANXIOUS;
        FolderItem folderItem2 = aFolderItem().id(1L).firstCategory(givenCategory).build();
        Folder oldFolder = aFolder().id(1L).build();
        folderItem1.setFolder(oldFolder);
        Folder newFolder = aFolder().build();
        folderItem2.setFolder(newFolder);

        spyFolderItemRepository.findByPostId_returnValue = folderItem1;
        spyFolderRepository.findById_returnValue = newFolder;
        spyFolderItemRepository.latestFolderItem_returnValue = folderItem1;

        folderItemService.moveFolderItem(1L, newFolder, new FolderItemMoveRequest("2"));
        assertThat(newFolder.getCoverImg()).isEqualTo(givenCategory.getImage());
    }

    @Test
    void updateFolderItem_passesFolderItemIdToRepository() {
        Folder newFolder = aFolder().build();
        FolderItem folderItem = aFolderItem().build();
        folderItem.setFolder(newFolder);
        spyFolderRepository.findById_returnValue = newFolder;
        spyFolderItemRepository.findByPostId_returnValue = folderItem;
        FolderItemUpdateRequest given = new FolderItemUpdateRequest(SecondCategory.ANXIOUS, "contect", List.of("tag1", "tag2", "tag3"),
                true, 1L);

        folderItemService.updateFolderItem(1L, "post id", newFolder, given);

        assertThat(spyFolderItemRepository.findByPostId_argumentPostId).isEqualTo("post id");
    }

    @Test
    void updateFolderItem_throwsNotExistsFolderItemException_whenFolderItemIsNotPresent() {
        Folder folder = aFolder().build();
        spyFolderItemRepository.findByPostId_returnValue = null;

        Assertions.assertThatThrownBy(() -> folderItemService.updateFolderItem(1L, "post id", folder, null))
                .isInstanceOf(NotExistsFolderItemException.class);
    }

    @Test
    void updateFolderItem_throwsException_whenMemberIdIsNotEqualsFolder() {
        Folder folder = aFolder().build();
        FolderItem folderItem = aFolderItem().build();
        spyFolderItemRepository.findByPostId_returnValue = folderItem;
        spyFolderItemRepository.save_returnValue = aFolderItem()
                .memberId(2L)
                .build();

        FolderItemUpdateRequest given = new FolderItemUpdateRequest(SecondCategory.ANXIOUS, "contect", List.of("tag1", "tag2", "tag3"),
                true, 1L);

        Assertions.assertThatThrownBy(() -> folderItemService.updateFolderItem(2L, "post id", folder, given))
                .isInstanceOf(InvalidUserException.class);
    }

    @Test
    void updateFolderItem_updateFolderItem() {
        Folder folder = aFolder().build();
        FolderItem folderItem = aFolderItem().build();
        folderItem.setFolder(folder);
        spyFolderRepository.findById_returnValue = folder;
        spyFolderItemRepository.findByPostId_returnValue = folderItem;
        FolderItemUpdateRequest given = new FolderItemUpdateRequest(SecondCategory.ANXIOUS, "new content", List.of("tag1", "tag2", "tag3"),
                false, 1L);

        folderItemService.updateFolderItem(1L, "post id", folder, given);

        assertThat(folderItem.getContent()).isEqualTo("new content");
        assertThat(folderItem.getSecondCategory()).isEqualTo(SecondCategory.ANXIOUS);
        assertThat(folderItem.getTags()).isEqualTo(List.of("tag1", "tag2", "tag3"));
        assertThat(folderItem.getDisclosure()).isEqualTo(false);
    }

    @Test
    void getRecentFolderItems_returnNullWhenFolderItemIsNull() {
        List<FolderItem> folderItems = folderItemService.getRecentFolderItems(1L);

        assertThat(folderItems).isEqualTo(Collections.emptyList());
    }

    @Test
    void getFolders_callsFindAllFoldersRepository() {
        Folder existedFolder = aFolder()
                .id(1L)
                .build();
        FolderItem existedFolderItem = aFolderItem().build();

        spyFolderItemRepository.latestFolderItems_argumentMemberId = 1L;
        spyFolderItemRepository.latestFolderItems_returnValue = Arrays.asList(existedFolderItem);
        spyFolderRepository.findAllByMemberId_returnValue = Arrays.asList(existedFolder);

        folderService.getFolders(1L);

        assertThat(spyFolderRepository.findAllByMemberId_argumentId).isEqualTo(1L);
    }

    @Test
    void getFolders_returnsFolders() {
        Folder folder1 = aFolder().build();
        Folder folder2 = aFolder().id(2L).build();
        FolderItem folderItem = aFolderItem().build();
        folderItem.setFolder(folder1);
        spyFolderRepository.findAllByMemberId_returnValue = new ArrayList<>(Arrays.asList(folder1, folder2));

        FoldersGetResponse result = folderService.getFolders(1L);

        assertThat(result.getFolders().get(0).getFolderId()).isEqualTo(1L);
        assertThat(result.getFolders().get(0).getFolderName()).isEqualTo("folder name");
        assertThat(result.getFolders().get(1).getFolderId()).isEqualTo(2L);

        assertThat(result.getPostsThumbnail().get(0)).isEqualTo(CoverImageUrl.defaultImage);
    }

    @Test
    void getFolderItems_returnsFolderItems() {
        Folder folder1 = aFolder().id(1L).build();
        FolderItem folderItem = aFolderItem().content("new post").views(12).build();
        folderItem.setFolder(folder1);

        spyFolderRepository.findAllByMemberId_returnValue = new ArrayList<>(Arrays.asList(folder1));
        spyFolderRepository.findById_returnValue = folder1;

        FolderItemsGetResponse folderItemsGetResponse = folderService.getFolderItems(1L, 1L, PageRequest.of(0, 20));

        assertThat(folderItemsGetResponse.getTotalCount()).isEqualTo(1);
        assertThat(folderItemsGetResponse.getPosts().get(0).getViews()).isEqualTo(12);
        assertThat(folderItemsGetResponse.getPosts().get(0).getContent()).isEqualTo("new post");
    }

    @Test
    void deleteFolderItem_throwsExceptionWhenFolderItemIsNotExisted() {
        Assertions.assertThatThrownBy(() -> folderItemService.deleteFolderItems(1L, List.of("1")))
                .isInstanceOf(NotExistsFolderItemException.class);
    }

    @Test
    void deleteFolderItem_callsDeleteFromRepository() {
        FolderItem folderItem1 = aFolderItem().id(1L).postId("1").firstCategory(FirstCategory.SADNESS).build();
        Folder folder = aFolder().id(1L).build();
        folderItem1.setFolder(folder);

        spyFolderItemRepository.findByPostId_returnValue = folderItem1;
        folderItemService.deleteFolderItems(1L, List.of("1"));

        assertThat(spyFolderItemRepository.findByPostId_argumentPostId).isEqualTo("1");
    }

    @Test
    public void increaseViews() {
        spyFolderItemRepository.findByPostId_returnValue = aFolderItem().views(0).build();

        folderItemService.increaseViews("1");

        assertThat(spyFolderItemRepository.findByPostId_returnValue.getViews()).isEqualTo(1);
    }
}
