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

    List<Article> articles = new ArrayList<>();

    public void initList() {
        for (int i = 1; i <= 100; i++) {
            articles.add(new Article((long) i, "test" + i));
        }
    }

    @GetMapping("")
    public List<Article> getArticles() {

        initList();

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable(value = "id") Long id) {

        for (int i = 0; i < this.articles.size(); i++) {
            if (articles.get(i).getId() == id) {
                return articles.get(i);
            }
        }

        return null;
    }

    @PostMapping("/{id}")
    public List<Article> createArticle(@PathVariable(value = "id") Long id) {

        for (int i = 0; i < this.articles.size(); i++) {
            if (!this.articles.get(i).getId().equals(id)) {
                Article article = new Article(id, "test" + id);
                this.articles.add(article);
                return this.articles;
            }
        }

        return this.articles;
    }

    @PatchMapping("/{id}")
    public Article patchArticle(@PathVariable(value = "id") Long id) {
        Article article = new Article(id, "test1");
        return article;
    }

    @PutMapping("/{id}")
    public Article putArticle(@PathVariable(value = "id") Long id) {
        Article article = new Article(id, "test1");
        return article;
    }

    @DeleteMapping("/{id}")
    public List<Article> deleteArticle(@PathVariable(value = "id") Long id) {

        for (int i = 0; i < this.articles.size(); i++) {
            if (this.articles.get(i).getId().equals(id)) {
                this.articles.remove(articles.get(i));
            }
        }

        return this.articles;
    }

}