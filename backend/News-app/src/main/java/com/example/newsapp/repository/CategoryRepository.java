package com.example.newsapp.repository;

import com.example.newsapp.entities.Category;

public interface CategoryRepository {
    Category insert(Category category);
    Category findByName(String name);

}
