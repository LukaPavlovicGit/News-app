package com.example.newsapp.entities;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tag {
    private Integer id;
    @NotNull(message = "keyword field is required")
    @NotEmpty(message = "keyword field is required")
    private String keyword;

    private Set<News> news = new HashSet<>();


    public Tag() {  }

    public Tag(String keyword, Set<News> news) {
        this.keyword = keyword;
        this.news = news;
    }

    public Tag(Integer id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }
}
