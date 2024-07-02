package com.example.demo.service;

import java.util.List;

import com.example.demo.AOPexception.Exception.GetNotFoundException;
import com.example.demo.AOPexception.Exception.PostIllegalArgumemtException;
import com.example.demo.AOPexception.Exception.PostNotFoundException;
import com.example.demo.AOPexception.Exception.PutNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            MemberRepository memberRepository,
            BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        try {
            Article article = articleRepository.findById(id)
                    .orElseThrow(IllegalArgumentException::new);
            ;
            Member member = memberRepository.findById(article.getAuthorId())
                    .orElseThrow(IllegalArgumentException::new);
            ;
            Board board = boardRepository.findById(article.getBoardId())
                    .orElseThrow(IllegalArgumentException::new);
            ;
            return ArticleResponse.of(article, member, board);
        } catch (RuntimeException e) {
            throw new GetNotFoundException(e.getMessage());
        }
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        try {
            List<Article> articles = articleRepository.findAllByBoard_Id(boardId);
            return articles.stream()
                    .map(article -> {
                        Member member = memberRepository.findById(article.getAuthorId())
                                .orElseThrow(IllegalArgumentException::new);
                        ;
                        Board board = boardRepository.findById(article.getBoardId())
                                .orElseThrow(IllegalArgumentException::new);
                        ;
                        return ArticleResponse.of(article, member, board);
                    })
                    .toList();
        } catch (RuntimeException e) {
            throw new GetNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = new Article(
                request.authorId(),
                boardRepository.getReferenceById(request.boardId()),
                request.title(),
                request.description()
        );
        if (article.getAuthorId() == null || article.getBoardId() == null ||
                article.getTitle() == null || article.getContent() == null) {
            throw new PostIllegalArgumemtException("NULL field existed");
        }
        try {
            Member member = memberRepository.findById(article.getAuthorId())
                    .orElseThrow(IllegalArgumentException::new);
            ;
            Board board = boardRepository.findById(article.getBoardId())
                    .orElseThrow(IllegalArgumentException::new);
            ;
            Article saved = articleRepository.save(article);
            return ArticleResponse.of(saved, member, board);
        } catch (RuntimeException e) {
            throw new PostNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        try {
            Article article = articleRepository.findById(id)
                    .orElseThrow(IllegalArgumentException::new);
            ;
            Board board = boardRepository.findById(request.boardId())
                    .orElseThrow(IllegalArgumentException::new);
            ;
            article.update(boardRepository.getReferenceById(request.boardId()), request.title(), request.description());
            Article updated = articleRepository.save(article);
            Member member = memberRepository.findById(updated.getAuthorId())
                    .orElseThrow(IllegalArgumentException::new);
            ;
            return ArticleResponse.of(article, member, board);
        } catch (RuntimeException e) {
            throw new PutNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
