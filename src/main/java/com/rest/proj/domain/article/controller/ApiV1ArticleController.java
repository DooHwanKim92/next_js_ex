package com.rest.proj.domain.article.controller;


import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.service.ArticleService;
import com.rest.proj.global.RsData.RsData;
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

    @GetMapping("")
    public RsData<List<Article>> getArticles() {
        List<Article> articles = this.articleService.findAll();
        return RsData.of("S-1","성공", articles);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable(value = "id") Long id) {

        return this.articleService.findById(id);
    }

    @PostMapping("/{id}")
    public List<Article> createArticle(@PathVariable(value = "id") Long id) {


        return this.articleService.findAll();
    }

    @PatchMapping("/{id}")
    public Article patchArticle(@PathVariable(value = "id") Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public Article putArticle(@PathVariable(value = "id") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public List<Article> deleteArticle(@PathVariable(value = "id") Long id) {

        return this.articleService.findAll();
    }

}