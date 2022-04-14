package depromeet.ohgzoo.iam.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {
    private MemberService memberService;
    private StubMemberRepository stubMemberRepository;

    @BeforeEach
    void setUp() {
        stubMemberRepository = new StubMemberRepository();
        memberService = new MemberService(stubMemberRepository);
    }

    @Test
    void alreadyJoin_returnsFalse_whenMemberIsNotExisted() {
        stubMemberRepository.findByIdToken_returnValue = null;

        assertThat(memberService.alreadyJoin("")).isFalse();
    }

    @Test
    void alreadyJoin_passesIdTokenToRepository() {
        memberService.alreadyJoin("givenToken");

        assertThat(stubMemberRepository.findByIdToken_argumentIdToken).isEqualTo("givenToken");
    }

    @Test
    void alreadyJoin_returnsTrue_whenMemberIsExisted() {
        stubMemberRepository.findByIdToken_returnValue = new Member();

        assertThat(memberService.alreadyJoin("")).isTrue();
    }

    @Test
    void join_callsSaveInMemberRepository() {
        MemberJoinRequest request = new MemberJoinRequest("profileImg", "nickname", "identifyToken");

        memberService.join(request);

        assertThat(stubMemberRepository.save_argumentMember.getId()).isNull();
        assertThat(stubMemberRepository.save_argumentMember.getIdentifyToken()).isEqualTo("identifyToken");
        assertThat(stubMemberRepository.save_argumentMember.getProfileImg()).isEqualTo("profileImg");
        assertThat(stubMemberRepository.save_argumentMember.getNickname()).isEqualTo("nickname");
    }

    @Test
    void getMemberId_passesIdTokenToRepository() {
        //given

        //when
        memberService.getMemberId("givenToken");

        //then
        assertThat(stubMemberRepository.findByIdToken_argumentIdToken).isEqualTo("givenToken");
    }

    @Test
    void getMemberId_returnsMemberId() {
        stubMemberRepository.findByIdToken_returnValue = Member.builder()
                .id(1L)
                .build();

        Long result = memberService.getMemberId("givenToken");

        assertThat(result).isEqualTo(1L);
    }

    @Test
    void getMemberId_returnsNull_whenMemberIsNotExisted() {
        stubMemberRepository.findByIdToken_returnValue = null;

        Long result = memberService.getMemberId("givenToken");

        assertThat(result).isNull();
    }
}