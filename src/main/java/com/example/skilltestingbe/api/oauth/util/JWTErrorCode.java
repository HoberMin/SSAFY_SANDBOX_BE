package com.example.skilltestingbe.api.oauth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JWTErrorCode {

    INVALID_TOKEN("유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN("만료된 토큰입니다");

    private final String message;
}
