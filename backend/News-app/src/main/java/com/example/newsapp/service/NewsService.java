package com.example.newsapp.service;

import com.example.newsapp.entities.News;
import com.example.newsapp.repository.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {
    @Inject
    private NewsRepository newsRepository;

    public News insert(News news){ return newsRepository.insert(news); }
    public News update(News news){ return newsRepository.update(news); }
    public News newsVisited(Integer id){ return newsRepository.newsVisited(id); }
    public News delete(Integer id){ return newsRepository.delete(id); }
    public List<News> mostRead(){ return newsRepository.mostRead(); }
    public List<News> findAllByCategory(String categoryName){ return newsRepository.findAllByCategory(categoryName); }
    public List<News> findAllByTag(String tagName){ return newsRepository.findAllByTag(tagName); }
    public News findById(Integer id) { return newsRepository.findById(id); }
    public List<News> findAll(int page){ return newsRepository.findAll(page); }
}
