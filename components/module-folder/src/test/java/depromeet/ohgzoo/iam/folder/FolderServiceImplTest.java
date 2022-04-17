package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.member.Member;
import depromeet.ohgzoo.iam.member.SpyMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class FolderServiceImplTest {

    private FolderService folderService;
    private SpyFolderRepository spyFolderRepository;
    private SpyJwtService spyJwtService;
    private SpyMemberRepository spyMemberRepository;


    @BeforeEach
    void setUp() {
        spyJwtService = new SpyJwtService();
        spyFolderRepository = new SpyFolderRepository();
        spyMemberRepository = new SpyMemberRepository();
        folderService = new FolderServiceImpl(spyJwtService, spyFolderRepository, spyMemberRepository);

        spyMemberRepository.findById_returnValue = Member.builder().build();
    }

    @Test
    void createFolder_extractMemberIdFromJwtService() {
        folderService.createFolder("givenToken", new FolderCreateRequest(""));

        assertThat(spyJwtService.getSubject_argumentToken).isEqualTo("givenToken");
    }

    @Test
    void createFolder_callsSaveInFolderRepository() {
        spyJwtService.getSubject_returnValue = "1";
        folderService.createFolder("givenToken", new FolderCreateRequest("folderName"));

        Folder savedFolder = spyFolderRepository.save_argumentFolder;
        assertThat(savedFolder.getId()).isNull();
        assertThat(savedFolder.getName()).isEqualTo("folderName");
        assertThat(savedFolder.getCoverImg()).isEqualTo("");
        assertThat(savedFolder.getMemberId()).isEqualTo(1L);
    }

    @Test
    void createFolder_returnsFolderResponse() {
        spyFolderRepository.save_returnValue = Folder.builder()
                .name("givenFolderName")
                .memberId(1L)
                .build();

        FolderResponse result = folderService.createFolder("AUTH_TOKEN", new FolderCreateRequest("givenFolderName"));

        assertThat(result.getFolderId()).isEqualTo(null);
        assertThat(result.getFolderName()).isEqualTo("givenFolderName");
    }

    @Test
    void deleteFolder_extractMemberIdFromJwtService() {
        folderService.deleteFolder("givenToken", 1L);

        assertThat(spyJwtService.getSubject_argumentToken).isEqualTo("givenToken");
    }

    @Test
    void deleteFolder_callsDeleteFromRepository() {
        spyJwtService.getSubject_returnValue = "1";
        folderService.deleteFolder("givenToken", 1L);

        assertThat(spyFolderRepository.delete_argumentFolderId).isNotNull();
    }

    @Test
    void deleteFolder_throwsExcetion_whenMemberIdIsNotEqualsFolder(){
        spyJwtService.getSubject_returnValue="2";

//folde.getMemberId == 1;
        Assertions.assertThatThrownBy(()-> folderService.deleteFolder("givenToken",1L))
                .isInstanceOf(InvalidUserException.class);

    }


    @Test
    void updateFolder_passesFolderIdToRepository() {
        folderService.updateFolder("", 1L, new UpdateFolderRequest(""));

        assertThat(spyFolderRepository.findById_argumentId).isEqualTo(1L);
    }

    @Test
    void updateFolder_throwsNotExistsFolderException_whenFolderIsNotPresent() {
        spyFolderRepository.findById_returnValue = null;

        Assertions.assertThatThrownBy(() -> folderService.updateFolder("", 1L, null))
                .isInstanceOf(NotExistsFolderException.class);
    }

    @Test
    void updateFolder_updateFolderName() {
        Folder givenFolder = Folder.builder()
                .name("oldName")
                .build();
        spyFolderRepository.findById_returnValue = givenFolder;

        folderService.updateFolder("", 1L, new UpdateFolderRequest("givenNewName"));

        assertThat(givenFolder.getName()).isEqualTo("givenNewName");
    }

    @Test
    void updateFolder_returnsFolderResponse() {
        Folder givenFolder = Folder.builder()
                .id(1L)
                .name("oldName")
                .build();
        spyFolderRepository.findById_returnValue = givenFolder;
        FolderResponse result = folderService.updateFolder("", 1L, new UpdateFolderRequest("givenNewName"));

        assertThat(result.getFolderId()).isEqualTo(1L);
        assertThat(result.getFolderName()).isEqualTo("givenNewName");
    }
}
