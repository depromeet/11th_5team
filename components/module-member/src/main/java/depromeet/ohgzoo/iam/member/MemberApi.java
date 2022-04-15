package depromeet.ohgzoo.iam.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @GetMapping("/users/me")
    public MemberResponse getMySelf(@RequestHeader("auth") String authToken) {
        return memberService.getMySelf(authToken);
    }
}
