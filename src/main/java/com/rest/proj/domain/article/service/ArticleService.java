package com.rest.proj.domain.article.service;


import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.repository.ArticleRepository;
import com.rest.proj.domain.member.entity.Member;
import com.rest.proj.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return this.articleRepository.findById(id);
    }

    @Transactional
    // 예외 발생 시 rollback 해준다
    public RsData<Article> create(Member member, String title, String content) {
        Article article = Article.builder()
                .author(member)
                .title(title)
                .content(content)
                .build();

        this.articleRepository.save(article);

        return RsData.of(
                "S-3",
                "게시글 등록 성공",
                article
        );
    }

    @Transactional
    public void modify(Article article, String title, String content) {
        Article modifyArticle = article.toBuilder()
                .title(title)
                .content(content)
                .build();

        this.articleRepository.save(modifyArticle);
    }

    @Transactional
    public void remove(Article article) {
        this.articleRepository.delete(article);
        // JPA 메서드 articleRepository.deleteById(id); 도 있음
    }
}