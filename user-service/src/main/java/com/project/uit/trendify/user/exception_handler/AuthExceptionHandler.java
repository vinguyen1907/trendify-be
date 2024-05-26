package com.project.uit.trendify.user.exception_handler;

import com.project.uit.trendify.common.lib.enums.ErrorCode;
import com.project.uit.trendify.user.exception_handler.errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<com.project.uit.trendify.user.exception_handler.errors.ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        apiError.setErrorCode(ErrorCode.USER_NOT_FOUND.getCode());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setErrorCode(ErrorCode.BAD_CREDENTIALS.getCode());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
