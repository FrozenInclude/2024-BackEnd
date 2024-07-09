package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    private String title;

    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

    public Article(
        Long id,
        Long authorId,
        Board board,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.authorId = authorId;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Article(Long authorId, Board board, String title, String content) {
        this.id = null;
        this.authorId = authorId;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Article() {

    }


    public void update(Board board, String title, String description) {
        this.board = board;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getBoardId() {
        return board.getId();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
