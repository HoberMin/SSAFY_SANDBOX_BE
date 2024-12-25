package com.example.skilltestingbe.api.paging.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @ArraySchema(schema = @Schema(implementation = ArticleResponse.class))
    private List<ArticleResponse> articles;

}
