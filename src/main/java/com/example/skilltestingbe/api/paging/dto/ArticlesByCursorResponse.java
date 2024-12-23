package com.example.skilltestingbe.api.paging.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class ArticlesByCursorResponse {

    private Long lastId;
    private List<ArticleResponse> articles;
}
