package depromeet.ohgzoo.iam.security.service;

import depromeet.ohgzoo.iam.security.dto.AuthToken;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;
import depromeet.ohgzoo.iam.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class TokenRefreshServiceImpl implements TokenRefreshService {

    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private final JwtService jwtService;

    @Override
    public AuthToken createNewAuthToken(HttpServletRequest request) throws UnAuthenticationException {
        String refreshToken = request.getHeader(REFRESH_TOKEN);

        if (StringUtils.hasText(refreshToken) && jwtService.verifyToken(refreshToken)) {
            String email = jwtService.getSubject(refreshToken);

            return new AuthToken(
                    jwtService.issuedToken(email, "USER", 3600),
                    jwtService.issuedToken(email, "USER", 36000)
            );
        } else {
            throw new UnAuthenticationException("토큰이 만료되었습니다.");
        }
    }
}