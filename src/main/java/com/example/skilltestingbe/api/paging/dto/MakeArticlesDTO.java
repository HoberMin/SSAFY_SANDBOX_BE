package com.example.skilltestingbe.api.paging.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MakeArticlesDTO {

    private List<ArticleDTO> articles;
}
