package com.example.newsapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class News {
    @NotNull(message = "title field is required")
    @NotEmpty(message = "title field is required")
    private String title;
    @NotNull(message = "content field is required")
    @NotEmpty(message = "content field is required")
    private String content;
    @NotNull(message = "author field is required")
    @NotEmpty(message = "author field is required")
    private String author;

    private Long createdAt;
    private int visits;
    private List<Comment> comments = new ArrayList<>();

    private Set<Tag> tags = new HashSet<>();


    public News() {  }

    public News(String title, String content, String author, Long createdAt, int visits, Set<Tag> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.visits = visits;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
