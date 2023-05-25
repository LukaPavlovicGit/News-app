package com.example.newsapp.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class News {
    private Integer id;
    @NotNull(message = "categoryName field is required")
    @NotEmpty(message = "categoryName field is required")
    private String categoryName;
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
    private String tags;


    public News() {  }

    public News(String categoryName, String title, String content, String author, Long createdAt, int visits) {
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.visits = visits;
    }

    public News(Integer id, String categoryName, String title, String content, String author, Long createdAt, int visits) {
        this.id = id;
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.visits = visits;
    }

    public News(Integer id, String categoryName, String title, String content, String author, Long createdAt, int visits, String tags) {
        this.id = id;
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.visits = visits;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
