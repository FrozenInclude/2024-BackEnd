package com.example.demo.repository;


import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryJpa implements ArticleRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Article> findAll() {
        return entityManager.createQuery("SELECT e FROM Article e", Article.class).getResultList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        try {
            return entityManager.createQuery("SELECT e FROM Article e WHERE e.boardId=:boardId", Article.class).
                    setParameter("boardId", boardId).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Article not found");
        }
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        try {
            return entityManager.createQuery("SELECT e FROM Article e WHERE e.authorId=:memberId", Article.class)
                    .setParameter("memberId", memberId).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Article not found");
        }
    }

    @Override
    public Article findById(Long id) {
        try {
            return entityManager.find(Article.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Article not found");
        }
    }

    @Override
    public Article insert(Article article) {
        entityManager.persist(article);
        return entityManager.find(Article.class, article.getId());
    }

    @Override
    public Article update(Article article) {
        Article art = findById(article.getId());
        art.update(article.getBoardId(), article.getTitle(), article.getContent());
        return art;
    }

    @Override
    public void deleteById(Long id) {
        Article article = findById(id);
        entityManager.remove(article);
    }
}
