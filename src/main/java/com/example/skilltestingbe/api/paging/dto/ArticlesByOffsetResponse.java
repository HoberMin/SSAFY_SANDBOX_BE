package com.example.skilltestingbe.api.paging.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class ArticlesByOffsetResponse {

    private Integer currentPageNumber;
    private Integer size;
    private Integer totalPage;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private List<ArticleResponse> articles;

}
