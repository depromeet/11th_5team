package depromeet.ohgzoo.iam.oauth.apple;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.member.MemberJoinRequest;
import depromeet.ohgzoo.iam.member.MemberService;
import depromeet.ohgzoo.iam.oauth.AuthToken;
import depromeet.ohgzoo.iam.oauth.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppleOauthService implements OauthService {


    private final AppleTokenDecoder appleTokenDecoder;
    private final MemberService memberService;
    private final JwtService jwtService;

    @Override
    public AuthToken getToken(final String code) {
        final AppleProfile profile = appleTokenDecoder.getProfile(code);
        final String identifyToken = String.valueOf(profile.getId());
        if (!memberService.alreadyJoin(identifyToken)) {
            memberService.join(mapToMemberJoinRequest(profile));
        }

        final Long memberId = memberService.getMemberId(identifyToken);

        return new AuthToken(
                jwtService.issuedToken(memberId.toString(), "USER", 3600 * 31 * 12),
                jwtService.issuedToken(memberId.toString(), "USER", 36000)
        );
    }

    private MemberJoinRequest mapToMemberJoinRequest(final AppleProfile profile) {
        return new MemberJoinRequest("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg", "익명", String.valueOf(profile.getId()));
    }

    @Override
    public AuthToken getRefreshToken(final String refreshToken) {
        return null;
    }
}
