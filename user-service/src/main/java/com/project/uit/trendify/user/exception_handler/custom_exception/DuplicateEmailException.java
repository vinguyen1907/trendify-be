package com.project.uit.trendify.user.exception_handler.custom_exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Email is already taken";

    public DuplicateEmailException() {
        super(DEFAULT_MESSAGE);
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
