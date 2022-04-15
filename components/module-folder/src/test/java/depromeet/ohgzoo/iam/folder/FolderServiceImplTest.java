package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.member.Member;
import depromeet.ohgzoo.iam.member.MemberNotExistsException;
import depromeet.ohgzoo.iam.member.SpyMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        folderService.createFolder("givenToken", "");

        assertThat(spyJwtService.getSubject_argumentToken).isEqualTo("givenToken");
    }

    @Test
    void createFolder_callsSaveInFolderRepository() {
        spyJwtService.getSubject_returnValue = "1";
        folderService.createFolder("givenToken","folderName");

        Folder savedFolder = spyFolderRepository.save_argumentFolder;
        assertThat(savedFolder.getId()).isNull();
        assertThat(savedFolder.getName()).isEqualTo("folderName");
        assertThat(savedFolder.getCoverImg()).isEqualTo("");
        assertThat(savedFolder.getMemberId()).isEqualTo(1L);
    }

    @Test
    void createFolder_returnsFolderResponse(){
        spyFolderRepository.save_returnValue = Folder.builder()
                .name("givenFolderName")
                .memberId(1L)
                .build();

        CreateFolderResponse result = folderService.createFolder("AUTH_TOKEN", "givenFolderName");

        assertThat(result.getFolderId()).isEqualTo(null);
        assertThat(result.getFolderName()).isEqualTo("givenFolderName");
    }
}
