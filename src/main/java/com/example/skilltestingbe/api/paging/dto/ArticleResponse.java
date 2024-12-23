package com.example.skilltestingbe.api.paging.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ArticleResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
}
