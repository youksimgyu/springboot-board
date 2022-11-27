package com.example.springbootboard.repository;

import com.example.springbootboard.config.Jpaconfig;
import com.example.springbootboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import(Jpaconfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,@Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select test")
    @Test
    void given_when_then_select() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(1000);
    }

    @DisplayName("insert test")
    @Test
    void given_when_then_insert() {
        // Given
        long previousCount = articleRepository.count();

        // When
        Article article = Article.of("new article", "new content", "#spring");
        Article savedArticle = articleRepository.save(article);

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update test")
    @Test
    void given_when_then_update() {
        // Given
        Article article1 = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article1.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article1);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete test")
    @Test
    void given_when_then_delete() {
        // Given
        Article article2 = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count(); // 게시물 1000개
        long previousArticleCommentCount = articleCommentRepository.count(); // 댓글 200개
        int deletedCommentsSize = article2.getArticleComments().size();

        // When
        articleRepository.delete(article2);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}