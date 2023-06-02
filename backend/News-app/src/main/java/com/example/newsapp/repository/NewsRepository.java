package com.example.newsapp.repository;

import com.example.newsapp.entities.News;

import java.util.List;

public interface NewsRepository {

    News insert(News news);
    News update(News news);
    News newsVisited(Integer newsId);
    News delete(Integer id);
    List<News> mostRead();
    List<News> findAllByCategory(Integer categoryId, Integer page);
    List<News> findAllByTag(String tagName);
    News findById(Integer id);
    List<News> findAll(int page);

}
