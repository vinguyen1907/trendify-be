package com.project.uit.trendify.common.lib.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATE_EMAIL("DUPLICATE_EMAIL"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    BAD_CREDENTIALS("BAD_CREDENTIALS");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }
}
