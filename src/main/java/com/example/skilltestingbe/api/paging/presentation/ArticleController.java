package com.example.skilltestingbe.api.paging.presentation;

import com.example.skilltestingbe.api.paging.dto.*;
import com.example.skilltestingbe.api.paging.service.ArticleService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
@Tag(name = "PAGING API", description = "Paging 페이지 API")
public class ArticleController {

    private final ArticleService articleService;
    private static final String DEFAULT_PAGE_NO = "1";
    private static final String DEFAULT_PAGE_SIZE = "6";
    private static final String DEFAULT_CURSOR_ID = "0";

    @GetMapping("/paging/offset")
    @Operation(summary = "Offset 기반 조회", description = "offset 기반으로 article 조회")
    @ApiResponse(responseCode = "200", description = "offset 기반으로 조회된 articles",
    content = @Content(schema = @Schema(implementation = ArticlesByOffsetResponse.class)))
    public ResponseEntity<?> findArticleByOffset(
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ArticleResponse> articles = articleService.findArticleByOffset(pageable);

        ArticlesByOffsetResponse articlesResponse = ArticlesByOffsetResponse.builder()
                .currentPageNumber(articles.getNumber())
                .size(articles.getSize())
                .totalPage(articles.getTotalPages())
                .hasNext(articles.hasNext())
                .hasPrevious(articles.hasPrevious())
                .articles(articles.getContent())
                .build();

        return ResponseEntity.ok().body(articlesResponse);
    }


    @GetMapping("/paging/cursor")
    @Operation(summary = "Cursor 기반 조회", description = "cursor 기반으로 article 조회")
    @ApiResponse(responseCode = "200", description = "cursor 기반으로 조회된 articles",
            content = @Content(schema = @Schema(implementation = ArticlesByCursorResponse.class)))
    public ResponseEntity<?> findArticleByCursor(
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = DEFAULT_CURSOR_ID) Long cursorId) {
        Pageable pageable = PageRequest.of(0, size);
        List<ArticleResponse> articles = articleService.findArticleByCursor(cursorId, pageable);
        Long lastId = !articles.isEmpty() ? articles.get(articles.size() - 1).getId() : cursorId;

        ArticlesByCursorResponse articlesResponse = ArticlesByCursorResponse.builder()
                .lastId(lastId)
                .articles(articles)
                .build();

        return ResponseEntity.ok().body(articlesResponse);
    }

    @Hidden
    @PostMapping("/make")
    public ResponseEntity<?> makeArticles(@RequestBody MakeArticlesDTO dto) {
        articleService.saveAll(dto.getArticles());
        return ResponseEntity.created(null).build();
    }
}
