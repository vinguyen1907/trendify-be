package com.project.uit.trendify.common.lib.enums;

import lombok.Getter;

@Getter
public enum TokenType {
    BEARER("Bearer");

    private final String tokenType;

    TokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
