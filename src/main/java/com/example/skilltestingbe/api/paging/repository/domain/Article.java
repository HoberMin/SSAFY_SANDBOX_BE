package com.example.skilltestingbe.api.paging.repository.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    private LocalDateTime createdAt;
}