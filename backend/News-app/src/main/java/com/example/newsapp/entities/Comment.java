package com.example.newsapp.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {
    private Integer id;
    @NotNull(message = "newsId field is required")
    @NotEmpty(message = "newsId field is required")
    private Integer newsId;
    @NotNull(message = "author field is required")
    @NotEmpty(message = "author field is required")
    private String author;
    @NotNull(message = "content field is required")
    @NotEmpty(message = "content field is required")
    private String content;

    private Long createdAt;

    public Comment() {  }
    public Comment(Integer id) { this.id = id; }

    public Comment(Integer id, Integer newsId, String author, String content, Long createdAt) {
        this.id = id;
        this.newsId = newsId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", newsId=" + newsId +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
