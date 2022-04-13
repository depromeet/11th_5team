package depromeet.ohgzoo.iam.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean alreadyJoin(String identifyToken) {
        return memberRepository.findByIdentifyToken(identifyToken)
                .isPresent();
    }

    public void join(MemberJoinRequest request) {
        Member member = new Member(request.getProfileImg(), request.getNickname(), request.getIdentifyToken());

        memberRepository.save(member);
    }

    public Long getMemberId(String identifyToken) {
        Optional<Member> maybeMember = memberRepository.findByIdentifyToken(identifyToken);
        return maybeMember.map(Member::getId)
                .orElse(null);
    }
}


