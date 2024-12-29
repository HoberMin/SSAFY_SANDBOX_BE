package com.example.skilltestingbe.api.oauth.repository.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private Long id;
    private String nickname;

    @Builder
    protected User(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
