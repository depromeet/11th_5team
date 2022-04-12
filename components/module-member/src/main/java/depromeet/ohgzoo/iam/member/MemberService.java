package depromeet.ohgzoo.iam.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean alreadyJoin(String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

    public void join(MemberJoinRequest request) {
        Member member = new Member(request.getProfileImg(), request.getNickname(), request.getEmail());

        memberRepository.save(member);
    }

    public Long getMemberId(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);
        return maybeMember.map(Member::getId)
                .orElse(null);
    }
}


