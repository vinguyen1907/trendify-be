package com.project.uit.trendify.user.exception_handler;

import com.project.uit.trendify.common.lib.enums.ErrorCode;
import com.project.uit.trendify.user.exception_handler.custom_exception.DuplicateEmailException;
import com.project.uit.trendify.user.exception_handler.errors.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CustomExceptionsHandler {
//    @ExceptionHandler(NotEnoughQuantityException.class)
//    public ResponseEntity<Object> handleNotEnoughQuantityException(NotEnoughQuantityException ex) {
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage(ex.getMessage());
//        apiError.setErrorCode("NOT_ENOUGH_QUANTITY");
//        return buildResponseEntity(apiError);
//    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setErrorCode(ErrorCode.DUPLICATE_EMAIL.getCode());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
