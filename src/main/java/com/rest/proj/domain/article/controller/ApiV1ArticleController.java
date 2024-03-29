package com.rest.proj.domain.article.controller;


import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.service.ArticleService;
import com.rest.proj.global.RsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// 콘트롤러 메서드에서 return하는 값을 기본적으로 문자열로 반환함
// like @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {

    private final ArticleService articleService;


    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse {
        private final List<Article> articles;
    }

    @Getter
    @AllArgsConstructor
    public static class ArticleResponse {
        private final Article article;
    }

    @GetMapping("")
    public RsData<ArticlesResponse> getArticles() {
        List<Article> articles = this.articleService.findAll();
        return RsData.of("S-1","성공", new ArticlesResponse(articles));
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable(value = "id") Long id) {
        return articleService.findById(id)
                .map(article -> RsData.of(
                                "S-1",
                                "성공",
                                new ArticleResponse(article)
                )).orElseGet(() -> RsData.of(
                                "F-1",
                                "%d번 게시글은 존재하지 않습니다.".formatted(id)
                ));
    }


}