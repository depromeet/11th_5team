package depromeet.ohgzoo.iam.member;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static depromeet.ohgzoo.iam.jwt.TokenName.AUTH_TOKEN;

@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @GetMapping("/users/me")
    public MemberResponse getMySelf(@RequestHeader(AUTH_TOKEN) String authToken) {
        return memberService.getMySelf(authToken);
    }

    @PatchMapping("/users/me")
    public void updateMember(@Login Long memberId,  @RequestBody UpdateMemberRequest request) {
        memberService.updateMember(request, memberId);
    }

    @DeleteMapping("/users/{memberId}")
    public void delete(@PathVariable Long memberId) {
        memberService.delete(memberId);
    }
}
