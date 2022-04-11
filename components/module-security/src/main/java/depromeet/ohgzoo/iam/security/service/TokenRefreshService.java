package depromeet.ohgzoo.iam.security.service;

import depromeet.ohgzoo.iam.security.dto.AuthToken;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;

import javax.servlet.http.HttpServletRequest;

public interface TokenRefreshService {
    AuthToken createNewAuthToken(HttpServletRequest request) throws UnAuthenticationException;
}