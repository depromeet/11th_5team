package depromeet.ohgzoo.iam.member;

import depromeet.ohgzoo.iam.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean alreadyJoin(String identifyToken) {
        return memberRepository.findByIdentifyToken(identifyToken)
                .isPresent();
    }

    @Override
    public void join(MemberJoinRequest request) {
        Member member = new Member(request.getProfileImg(), request.getNickname(), request.getIdentifyToken());

        Member savedMember = memberRepository.save(member);

        eventPublisher.publishEvent(new MemberCreateEvent(this, savedMember.getId()));
    }

    @Override
    public Long getMemberId(String identifyToken) {
        Optional<Member> maybeMember = memberRepository.findByIdentifyToken(identifyToken);
        return maybeMember.map(Member::getId)
                .orElse(null);
    }

    @Override
    public MemberResponse getMySelf(String token) {
        Long memberId = getMemberIdByToken(token);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoExistsMemberException::new);

        return new MemberResponse(member.getProfileImg(), member.getNickname());
    }

    @Transactional
    @Override
    public void updateMember(final UpdateMemberRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoExistsMemberException::new);

        member.updateMember(request);
    }

    @Override
    public void delete(Long memberId) {
        eventPublisher.publishEvent(new MemberDeleteEvent(this, memberId));
    }

    private Long getMemberIdByToken(String token) {
        String subject = jwtService.getSubject(token);
        Long memberId = Long.valueOf(subject);
        return memberId;
    }
}


