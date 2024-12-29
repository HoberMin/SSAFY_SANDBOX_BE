package com.example.skilltestingbe.api.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@Getter
public class KakaoUserInfoResponseDto {

    private Long id;

    @JsonProperty("connected_at")
    private Date connectedAt;

    @JsonProperty("properties")
    private Properties properties;


    @JsonProperty("kakao_account")
    private KakoAccount kakaoAccount;

    @Getter
    public class Properties {

        private String nickname;
    }

    @Getter
    public class KakoAccount {

        @JsonProperty("profile_nickname_needs_agreement")
        private Boolean profileNicknameNeedsAgreement;

        @JsonProperty("profile")
        private Profile profile;

        @Getter
        public class Profile {

            private String nickname;

            @JsonProperty("is_default_nickname")
            private Boolean isDefaultNickname;
        }
    }
}
