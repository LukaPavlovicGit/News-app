package com.example.newsapp.repository;

import com.example.newsapp.entities.Tag;

import java.util.List;

public interface TagRepository {

    Tag insert(Tag tag);
    Tag findByKeyword(String keyword);
    List<Tag> getAll();
}
