package depromeet.ohgzoo.iam.security.controller;

import depromeet.ohgzoo.iam.security.dto.CommonResult;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult authenticationExHandle(UnAuthenticationException ex) {
        return new CommonResult(ex.getMessage(), "401");
    }
}