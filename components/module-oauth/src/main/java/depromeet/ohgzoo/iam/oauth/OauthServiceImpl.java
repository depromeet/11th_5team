package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.jwt.UnAuthenticationException;
import depromeet.ohgzoo.iam.member.MemberJoinRequest;
import depromeet.ohgzoo.iam.member.MemberService;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoClient;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenRequest;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OauthServiceImpl implements OauthService {
    @Value("${kakaoClientId}")
    private String clientId;

    @Value("${kakaoRedirectUrl}")
    private String redirectUrl;

    private final KakaoClient kakaoClient;
    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    public Oauth2LoginUrl getLoginUrl() {
        return new Oauth2LoginUrl(
                new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                        .append("?client_id=").append(clientId)
                        .append("&response_type=code")
                        .append("&redirect_uri=").append(redirectUrl)
                        .toString()
        );
    }

    @Override
    public AuthToken getToken(String code) {
        KakaoTokenResponse kakaoToken = getKakaoToken(code);
        KakaoProfileResponse profile = getKakaoProfile(kakaoToken);

        String identifyToken = String.valueOf(profile.getId());
        if (!memberService.alreadyJoin(identifyToken)) {
            memberService.join(mapToMemberJoinRequest(profile));
        }

        Long memberId = memberService.getMemberId(identifyToken);

        return new AuthToken(
                jwtService.issuedToken(memberId.toString(), "USER", 3600 * 31 * 12),
                jwtService.issuedToken(memberId.toString(), "USER", 36000)
        );
    }

    private MemberJoinRequest mapToMemberJoinRequest(KakaoProfileResponse profile) {
        KakaoProfileResponse.KakaoAccount account = profile.getAccount();
        return new MemberJoinRequest(account.getProfile().getProfileImg(), account.getProfile().getNickname(), String.valueOf(profile.getId()));
    }

    @Override
    public AuthToken getRefreshToken(String refreshToken) {
        if (!jwtService.verifyToken(refreshToken)) {
            throw new UnAuthenticationException("토큰이 만료되었습니다.");
        }

        String memberId = jwtService.getSubject(refreshToken);
        return new AuthToken(
                jwtService.issuedToken(memberId, "USER", 3600),
                jwtService.issuedToken(memberId, "USER", 36000));
    }

    private KakaoProfileResponse getKakaoProfile(KakaoTokenResponse kakaoToken) {
        return kakaoClient.getProfile("Bearer " + kakaoToken.getAccessToken());
    }

    private KakaoTokenResponse getKakaoToken(String code) {
        KakaoTokenRequest kakaoTokenRequest = new KakaoTokenRequest(clientId, redirectUrl, code);
        return kakaoClient.getToken(kakaoTokenRequest);
    }
}

