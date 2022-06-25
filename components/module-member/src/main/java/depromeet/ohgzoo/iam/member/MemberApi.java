package depromeet.ohgzoo.iam.member;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static depromeet.ohgzoo.iam.jwt.TokenName.AUTH_TOKEN;

@RequiredArgsConstructor
@RequestMapping("/users/me")
@RestController
public class MemberApi {
    private final MemberService memberService;

    @GetMapping
    public MemberResponse getMySelf(@RequestHeader(AUTH_TOKEN) String authToken) {
        return memberService.getMySelf(authToken);
    }

    @PatchMapping
    public void updateMember(@Login Long memberId,  @RequestBody UpdateMemberRequest request) {
        memberService.updateMember(request, memberId);
    }
}
