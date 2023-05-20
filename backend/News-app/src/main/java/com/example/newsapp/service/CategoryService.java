package com.example.newsapp.service;

import com.example.newsapp.entities.Category;
import com.example.newsapp.repository.CategoryRepository;

import javax.inject.Inject;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category insert(Category category){ return categoryRepository.insert(category); }
    public Category findByName(String name) { return categoryRepository.findByName(name); }

}
