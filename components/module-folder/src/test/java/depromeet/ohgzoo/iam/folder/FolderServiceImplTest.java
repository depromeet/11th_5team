package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.exception.ExistedNameException;
import depromeet.ohgzoo.iam.folder.exception.InvalidUserException;
import depromeet.ohgzoo.iam.folder.exception.NotExistsFolderException;
import depromeet.ohgzoo.iam.member.Member;
import depromeet.ohgzoo.iam.member.SpyMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static depromeet.ohgzoo.iam.folder.FolderFixtures.aFolder;
import static org.assertj.core.api.Assertions.assertThat;

public class FolderServiceImplTest {

    private FolderService folderService;
    private SpyFolderRepository spyFolderRepository;
    private SpyFolderItemRepository spyFolderItemRepository;
    private SpyMemberRepository spyMemberRepository;

    @BeforeEach
    void setUp() {
        spyFolderRepository = new SpyFolderRepository();
        spyMemberRepository = new SpyMemberRepository();
        spyFolderItemRepository = new SpyFolderItemRepository();
        folderService = new FolderServiceImpl(spyFolderRepository,spyFolderItemRepository);

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
    void deleteFolder_callsDeleteFromRepository() {
        folderService.deleteFolder(1L, 1L);

        assertThat(spyFolderRepository.delete_argumentFolderId).isNotNull();
    }

    @Test
    void deleteFolder_throwsException_whenMemberIdIsNotEqualsFolder() {
        spyFolderRepository.save_returnValue = aFolder()
                .memberId(1L)
                .build();

        Assertions.assertThatThrownBy(() -> folderService.deleteFolder(2L, 1L))
                .isInstanceOf(InvalidUserException.class);
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
        folderService.createFolderItem(1L,1L, new FolderItemCreateRequest(FirstCategory.ANGRY,SecondCategory.ANXIOUS,"post content",null,false));

        FolderItem savedFolderItem = spyFolderItemRepository.save_argumentFolderItem;
        assertThat(savedFolderItem.getId()).isNull();
        assertThat(savedFolderItem.getContent()).isEqualTo("post content");
    }

    @Test
    void createFolderItem_callsAddInFolderRepository(){
        //folderRepository에 folderItem 넣기
        //test : 해당 folder에 folderItem 있는지
        Folder existedFolder = aFolder()
                .id(1L)
                .build();
        spyFolderRepository.findById_returnValue = existedFolder;
        folderService.createFolderItem(1L,1L, new FolderItemCreateRequest(FirstCategory.ANGRY,SecondCategory.ANXIOUS,"post content",null,false));

        assertThat(existedFolder.getFolderItems().get(0).getContent()).isEqualTo("post content");
    }

}
