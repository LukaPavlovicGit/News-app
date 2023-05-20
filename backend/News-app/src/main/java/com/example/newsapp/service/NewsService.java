package com.example.newsapp.service;

import com.example.newsapp.entities.News;
import com.example.newsapp.repository.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {
    @Inject
    private NewsRepository newsRepository;

    public News insert(News news){ return newsRepository.insert(news); }
    public News findById(Integer id) { return newsRepository.findById(id); }
    public List<News> findAll(){ return newsRepository.findAll(); }
    public List<News> findAllByCategory(String categoryName){ return newsRepository.findAllCategory(categoryName); }
}
