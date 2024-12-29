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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/authorization")
public class OauthControllerByHeader {

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
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/member")
    public ResponseEntity<?> getNickname(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            VerifyTokenResponseDto response = userService.getNickname(token, true);
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
            @RequestHeader(value = "X-Refresh", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ReIssueTokenDto response = userService.reissueToken(token);
            return ResponseEntity.ok().body(response);
        } catch (JwtException e) {
            if (e.getMessage().equals(JWTErrorCode.INVALID_TOKEN.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
