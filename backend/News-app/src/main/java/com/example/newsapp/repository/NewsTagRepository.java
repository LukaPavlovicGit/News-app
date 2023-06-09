package com.example.newsapp.repository;

import com.example.newsapp.entities.NewsTag;

import java.util.List;

public interface NewsTagRepository {

    NewsTag insert(NewsTag newsTag);
    void deleteByNewsId(Integer newsId);
    List<NewsTag> findAllByNewsId(Integer newsId);
    List<NewsTag> findAllByTagId(Integer tagId);

}
