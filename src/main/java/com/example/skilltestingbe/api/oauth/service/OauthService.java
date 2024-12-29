package com.example.skilltestingbe.api.oauth.service;

import com.example.skilltestingbe.api.oauth.dto.KakaoTokenResponseDto;
import com.example.skilltestingbe.api.oauth.dto.KakaoUserInfoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OauthService {

    private final String KAUTH_USER_URL_HOST;
    private final String KAUTH_USER_URL_PATH;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_TOKEN_URL_PATH;
    private final String CLIENT_ID;
    private final String REDIRECT_URI;

    public OauthService(@Value("${KAUTH.USER.URL.HOST}") String KAUTH_USER_URL_HOST,
                        @Value("${KAUTH.TOKEN.URL.HOST}") String KAUTH_TOKEN_URL_HOST,
                        @Value("${KAUTH.TOKEN.URL.PATH}") String KAUTH_TOKEN_URL_PATH,
                        @Value("${CLIENT.ID}") String CLIENT_ID,
                        @Value("${REDIRECT.URI}") String REDIRECT_URI,
                        @Value("${KAUTH.USER.URL.PATH}") String KAUTH_USER_URL_PATH) {
        this.KAUTH_USER_URL_HOST = KAUTH_USER_URL_HOST;
        this.KAUTH_TOKEN_URL_HOST = KAUTH_TOKEN_URL_HOST;
        this.KAUTH_TOKEN_URL_PATH = KAUTH_TOKEN_URL_PATH;
        this.KAUTH_USER_URL_PATH = KAUTH_USER_URL_PATH;
        this.CLIENT_ID = CLIENT_ID;
        this.REDIRECT_URI = REDIRECT_URI;
    }

    public String getAccessToken(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path(KAUTH_TOKEN_URL_PATH)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("redirect_uri", REDIRECT_URI)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        return kakaoTokenResponseDto.getAccessToken();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path(KAUTH_USER_URL_PATH)
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        return userInfo;
    }
}
