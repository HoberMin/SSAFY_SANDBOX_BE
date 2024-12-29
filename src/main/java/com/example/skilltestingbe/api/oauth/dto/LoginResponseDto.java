package com.example.skilltestingbe.api.oauth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    String accessToken;
    String refreshToken;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
