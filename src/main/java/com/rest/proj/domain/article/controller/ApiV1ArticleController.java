package com.rest.proj.domain.article.controller;


import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.service.ArticleService;
import com.rest.proj.domain.member.service.MemberService;
import com.rest.proj.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// 콘트롤러 메서드에서 return하는 값을 기본적으로 문자열로 반환함
// like @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {

    private final ArticleService articleService;

    private final MemberService memberService;

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
        return RsData.of("S-1", "성공", new ArticlesResponse(articles));
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

    @Data
    // @Data = getter, setter 등 자주 사용하는 어노테이션 패키징
    // 매핑된 메서드에서 @RequestBody로 받은 Json객체를 담는 객체
    public static class CreateRequest {
        @NotBlank
        // @Valid
        private String title;
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    // 백엔드 로직 처리 후 결과를 담아서 보여준다.
    // db에 접근하지는 않음
    public static class CreateResponse {
        private final Article article;
    }

    @PostMapping("")
    public RsData<CreateResponse> createArticle(@Valid @RequestBody CreateRequest createRequest) {
        RsData<Article> createRs = this.articleService.create(null, createRequest.getTitle(), createRequest.getContent());

        if (createRs.isFail()) return (RsData) createRs;

        return RsData.of(
                createRs.getResultCode(),
                createRs.getMsg(),
                new CreateResponse(createRs.getData())
        );
    }

    @Data
    public static class ModifyRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class ModifyResponse {
        private final Article article;
    }

    @PatchMapping("/{id}")
    public RsData<ModifyResponse> modifyArticle(@PathVariable(value = "id") Long id, @Valid @RequestBody ModifyRequest modifyRequest) {

        // 게시글 수정 권한 검증 로직 필요

        Optional<Article> article = this.articleService.findById(id);
        if (article.isEmpty()) {
            return RsData.of(
                    "F-1",
                    "%d번 게시글은 존재하지 않습니다.".formatted(id)
            );
        }

        this.articleService.modify(article.get(), modifyRequest.getTitle(), modifyRequest.getContent());

        return RsData.of(
                "S-1",
                "%d 번 게시글 수정 성공".formatted(article.get().getId()),
                new ModifyResponse(article.get())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<Object> removeArticle(@PathVariable(value = "id") Long id) {

        // 게시글 삭제 권한 검증 로직 필요

        Optional<Article> article = this.articleService.findById(id);
        if (article.isEmpty()) {
            return RsData.of(
                    "F-1",
                    "%d번 게시글은 존재하지 않습니다.".formatted(id)
            );
        }

        this.articleService.remove(article.get());

        return RsData.of(
                "S-5",
                "게시글 삭제 성공",
                article
        );
    }

}