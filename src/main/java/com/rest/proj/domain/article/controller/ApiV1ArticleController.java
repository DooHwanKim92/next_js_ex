package com.rest.proj.domain.article.controller;


import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.service.ArticleService;
import com.rest.proj.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @Getter
    public static class CreateRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class CreateResponse {
        private final Article article;
    }

    @PostMapping("")
    public RsData<CreateResponse> createArticle(@Valid @RequestBody CreateRequest createRequest) {

        Article article = this.articleService.create(createRequest.getTitle(), createRequest.getContent());

        return RsData.of(
                "S-1",
                "게시글 등록 성공",
                new CreateResponse(article)
        );
    }

//    @PostMapping("")
//    public RsData<ArticleResponse> createArticle(@RequestBody Article article) {
//        return this.articleService.create(article)
//                .map(article -> RsData.of(
//                        "S-1",
//                        "성공",
//                        new ArticleResponse(article)
//                )).orElseGet()
//
//    }
}