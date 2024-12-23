package com.example.skilltestingbe.api.paging.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleDTO {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
}
