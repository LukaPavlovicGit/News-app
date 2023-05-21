package com.example.newsapp.repository;

import com.example.newsapp.entities.News;

import java.util.List;

public interface NewsRepository {

    News insert(News news);
    News delete(Integer id);
    News findById(Integer id);
    List<News> findAll();
    List<News> findAllCategory(String categoryName);

}
