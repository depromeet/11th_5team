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
        stubMemberRepository.findByEmail_returnValue = null;

        assertThat(memberService.alreadyJoin("")).isFalse();
    }

    @Test
    void alreadyJoin_passesEmailToRepository() {
        memberService.alreadyJoin("givenEmail");

        assertThat(stubMemberRepository.findByEmail_argumentEmail).isEqualTo("givenEmail");
    }

    @Test
    void alreadyJoin_returnsTrue_whenMemberIsExisted() {
        stubMemberRepository.findByEmail_returnValue = new Member();

        assertThat(memberService.alreadyJoin("")).isTrue();
    }

    @Test
    void join_callsSaveInMemberRepository() {
        MemberJoinRequest request = new MemberJoinRequest("profileImg", "nickname", "email");

        memberService.join(request);

        assertThat(stubMemberRepository.save_argumentMember.getId()).isNull();
        assertThat(stubMemberRepository.save_argumentMember.getEmail()).isEqualTo("email");
        assertThat(stubMemberRepository.save_argumentMember.getProfileImg()).isEqualTo("profileImg");
        assertThat(stubMemberRepository.save_argumentMember.getNickname()).isEqualTo("nickname");
    }

    @Test
    void getMemberId_passesEmailToRepository() {
        //given

        //when
        memberService.getMemberId("givenEmail");

        //then
        assertThat(stubMemberRepository.findByEmail_argumentEmail).isEqualTo("givenEmail");
    }

    @Test
    void getMemberId_returnsMemberId() {
        stubMemberRepository.findByEmail_returnValue = Member.builder()
                .id(1L)
                .build();

        Long result = memberService.getMemberId("");

        assertThat(result).isEqualTo(1L);
    }

    @Test
    void getMemberId_returnsNull_whenMemberIsNotExisted() {
        stubMemberRepository.findByEmail_returnValue = null;

        Long result = memberService.getMemberId("");

        assertThat(result).isNull();
    }
}