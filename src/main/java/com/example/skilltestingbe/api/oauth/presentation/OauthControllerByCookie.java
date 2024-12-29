package com.example.skilltestingbe.api.oauth.presentation;

import com.example.skilltestingbe.api.oauth.dto.*;
import com.example.skilltestingbe.api.oauth.service.OauthService;
import com.example.skilltestingbe.api.oauth.service.UserService;
import com.example.skilltestingbe.api.oauth.util.JWTErrorCode;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/cookie")
public class OauthControllerByCookie {

    private static final int ACCESS_EXPIRATION_TIME = 1000 * 60 * 10;
    private static final int REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24;
    private static final int EXPIRED_TIME = 0;

    private static final String ACCESS_TOKEN_NAME = "accessToken";
    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final String SAME_SITE_OPTION = "None";
    private static final String COOKIE_PATH = "/";

    private final OauthService oauthService;
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDto dto) {
        if (dto.getCode() == null) {
            return ResponseEntity.badRequest().build();
        }

        String accessToken = oauthService.getAccessToken(dto.getCode());
        KakaoUserInfoResponseDto userInfo = oauthService.getUserInfo(accessToken);
        LoginResponseDto result = userService.getToken(userInfo);

        ResponseCookie access = ResponseCookie
                .from(ACCESS_TOKEN_NAME, result.getAccessToken())
                .sameSite(SAME_SITE_OPTION)
                .httpOnly(true)
                .secure(true)
                .path(COOKIE_PATH)
                .maxAge(ACCESS_EXPIRATION_TIME)
                .build();

        ResponseCookie refresh = ResponseCookie
                .from(REFRESH_TOKEN_NAME, result.getRefreshToken())
                .sameSite(SAME_SITE_OPTION)
                .httpOnly(true)
                .secure(true)
                .path(COOKIE_PATH)
                .maxAge(REFRESH_EXPIRATION_TIME)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, access.toString())
                .header(HttpHeaders.SET_COOKIE, refresh.toString())
                .build();
    }

    @GetMapping("/member")
    public ResponseEntity<?> getNickname(
            @CookieValue(name = ACCESS_TOKEN_NAME) String token
    ) {
        try {
            VerifyTokenResponseDto response = userService.getNickname(token, false);
            return ResponseEntity.ok().body(response);
        } catch (JwtException e) {
            if (e.getMessage().equals(JWTErrorCode.INVALID_TOKEN.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(
            @CookieValue(name = REFRESH_TOKEN_NAME) String refreshToken) {
        try {
            ReIssueTokenDto response = userService.reissueToken(refreshToken);

            ResponseCookie access = ResponseCookie
                    .from(ACCESS_TOKEN_NAME, response.getAccessToken())
                    .sameSite(SAME_SITE_OPTION)
                    .httpOnly(true)
                    .secure(true)
                    .path(COOKIE_PATH)
                    .maxAge(ACCESS_EXPIRATION_TIME)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, access.toString())
                    .build();
        } catch (JwtException e) {
            if (e.getMessage().equals(JWTErrorCode.INVALID_TOKEN.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(name = REFRESH_TOKEN_NAME) String refreshToken) {
        try {
            userService.reissueToken(refreshToken);

            ResponseCookie access = ResponseCookie
                    .from(ACCESS_TOKEN_NAME, "")
                    .sameSite(SAME_SITE_OPTION)
                    .httpOnly(true)
                    .secure(true)
                    .path(COOKIE_PATH)
                    .maxAge(EXPIRED_TIME)
                    .build();

            ResponseCookie refresh = ResponseCookie
                    .from(REFRESH_TOKEN_NAME, "")
                    .sameSite(SAME_SITE_OPTION)
                    .httpOnly(true)
                    .secure(true)
                    .path(COOKIE_PATH)
                    .maxAge(EXPIRED_TIME)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, access.toString())
                    .header(HttpHeaders.SET_COOKIE, refresh.toString())
                    .build();
        } catch (JwtException e) {
            if (e.getMessage().equals(JWTErrorCode.INVALID_TOKEN.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
