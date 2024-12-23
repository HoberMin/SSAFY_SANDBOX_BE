package com.example.skilltestingbe.api.paging.repository;

import com.example.skilltestingbe.api.paging.dto.ArticleResponse;
import com.example.skilltestingbe.api.paging.repository.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<ArticleResponse> findPageBy(Pageable pageable);
    List<ArticleResponse> findByIdGreaterThanOrderByIdAsc(Long cursorId, Pageable pageable);

}
