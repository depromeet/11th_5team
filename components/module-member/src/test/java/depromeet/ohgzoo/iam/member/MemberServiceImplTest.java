package depromeet.ohgzoo.iam.member;

import depromeet.ohgzoo.iam.jwt.SpyJwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceImplTest {
    private MemberServiceImpl memberService;
    private SpyMemberRepository spyMemberRepository;
    private SpyJwtService spyJwtService;

    @BeforeEach
    void setUp() {
        spyJwtService = new SpyJwtService();
        spyMemberRepository = new SpyMemberRepository();
        memberService = new MemberServiceImpl(spyMemberRepository, spyJwtService);
    }

    @Test
    void alreadyJoin_returnsFalse_whenMemberIsNotExisted() {
        spyMemberRepository.findByIdToken_returnValue = null;

        assertThat(memberService.alreadyJoin("")).isFalse();
    }

    @Test
    void alreadyJoin_passesIdTokenToRepository() {
        memberService.alreadyJoin("givenToken");

        assertThat(spyMemberRepository.findByIdToken_argumentIdToken).isEqualTo("givenToken");
    }

    @Test
    void alreadyJoin_returnsTrue_whenMemberIsExisted() {
        spyMemberRepository.findByIdToken_returnValue = new Member();

        assertThat(memberService.alreadyJoin("")).isTrue();
    }

    @Test
    void join_callsSaveInMemberRepository() {
        MemberJoinRequest request = new MemberJoinRequest("profileImg", "nickname", "identifyToken");

        memberService.join(request);

        assertThat(spyMemberRepository.save_argumentMember.getId()).isNull();
        assertThat(spyMemberRepository.save_argumentMember.getIdentifyToken()).isEqualTo("identifyToken");
        assertThat(spyMemberRepository.save_argumentMember.getProfileImg()).isEqualTo("profileImg");
        assertThat(spyMemberRepository.save_argumentMember.getNickname()).isEqualTo("nickname");
    }

    @Test
    void getMemberId_passesIdTokenToRepository() {
        //given

        //when
        memberService.getMemberId("givenToken");

        //then
        assertThat(spyMemberRepository.findByIdToken_argumentIdToken).isEqualTo("givenToken");
    }

    @Test
    void getMemberId_returnsMemberId() {
        spyMemberRepository.findByIdToken_returnValue = Member.builder()
                .id(1L)
                .build();

        Long result = memberService.getMemberId("givenToken");

        assertThat(result).isEqualTo(1L);
    }

    @Test
    void getMemberId_returnsNull_whenMemberIsNotExisted() {
        spyMemberRepository.findByIdToken_returnValue = null;

        Long result = memberService.getMemberId("givenToken");

        assertThat(result).isNull();
    }

    @Test
    void getMySelf_passesTokenToJwtService() {
        memberService.getMySelf("givenToken");

        assertThat(spyJwtService.getSubject_argumentToken).isEqualTo("givenToken");
    }

    @Test
    void getMySelf_passesUserIdToRepository() {
        spyJwtService.getSubject_returnValue = "1";

        memberService.getMySelf("");

        assertThat(spyMemberRepository.findById_argumentId).isEqualTo(1L);
    }

    @Test
    void getMySelf_throwsNoExistsMemberException_whenInvalidMemberId() {
        spyMemberRepository.findById_returnValue = null;

        Assertions.assertThatThrownBy(() -> memberService.getMySelf(""))
                .isInstanceOf(NoExistsMemberException.class);
    }

    @Test
    void getMySelf_returnsMemberResponse() {
        spyMemberRepository.findById_returnValue = Member.builder()
                .nickname("givenNickName")
                .profileImg("givenProfile")
                .build();

        MemberResponse result = memberService.getMySelf("");

        assertThat(result.getNickname()).isEqualTo("givenNickName");
        assertThat(result.getProfileImg()).isEqualTo("givenProfile");
    }
}