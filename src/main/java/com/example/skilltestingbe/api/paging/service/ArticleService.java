package com.example.skilltestingbe.api.paging.service;

import com.example.skilltestingbe.api.paging.dto.ArticleDTO;
import com.example.skilltestingbe.api.paging.dto.ArticleResponse;
import com.example.skilltestingbe.api.paging.repository.ArticleRepository;
import com.example.skilltestingbe.api.paging.repository.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleResponse> findArticleByOffset(Pageable pageable) {
        return articleRepository.findPageBy(pageable);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> findArticleByCursor(Long cursorId, Pageable pageable) {
        return articleRepository.findByIdGreaterThanOrderByIdAsc(cursorId, pageable);
    }

    public void saveAll(List<ArticleDTO> articles) {
        List<Article> data = new ArrayList<>();
        for (ArticleDTO dto : articles) {
            data.add(Article.builder()
                            .id(dto.getId())
                            .title(dto.getTitle())
                            .createdAt(dto.getCreatedAt())
                    .build());
        }

        articleRepository.saveAll(data);
    }
}
