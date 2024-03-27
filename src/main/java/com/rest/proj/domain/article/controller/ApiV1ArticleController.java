package com.rest.proj.domain.article.controller;


import com.rest.proj.domain.article.entity.Article;
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

    @GetMapping("")
    public List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();

        articles.add(new Article(1L));
        articles.add(new Article(2L));
        articles.add(new Article(3L));

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable(value = "id")Long id) {
        Article article = new Article(id);

        return article;
    }

    @PostMapping("/{id}")
    public Article createArticle(@PathVariable(value = "id")Long id) {
        Article article = new Article(id);

        return article;
    }

    @PatchMapping("/{id}")
    public Article patchArticle(@PathVariable(value = "id")Long id) {
        Article article = new Article(id);

        return article;
    }

    @DeleteMapping("/{id}")
    public Article deleteArticle(@PathVariable(value = "id")Long id) {
        Article article = new Article(id);

        return article;
    }

}