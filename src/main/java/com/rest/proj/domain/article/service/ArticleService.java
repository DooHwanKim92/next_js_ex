package com.rest.proj.domain.article.service;


import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void create(String title, String content) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();

        this.articleRepository.save(article);
    }
}
