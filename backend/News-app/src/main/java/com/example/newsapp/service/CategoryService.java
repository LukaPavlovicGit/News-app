package com.example.newsapp.service;

import com.example.newsapp.entities.Category;
import com.example.newsapp.repository.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public List<Category> getAll(Integer page){ return categoryRepository.getAll(page); }
    public Category getById(Integer id){ return categoryRepository.getById(id); }
    public Category insert(Category category){ return categoryRepository.insert(category); }
    public Category update(Category category) { return categoryRepository.update(category); }
    public Category delete(Integer id) { return categoryRepository.delete(id); }

}
