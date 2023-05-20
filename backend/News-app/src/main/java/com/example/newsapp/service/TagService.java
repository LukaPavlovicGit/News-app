package com.example.newsapp.service;

import com.example.newsapp.entities.Tag;
import com.example.newsapp.repository.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {
    @Inject
    private TagRepository tagRepository;

    public Tag insert(Tag tag){ return tagRepository.insert(tag); }

    public Tag findByKeyword(String keyword){ return tagRepository.findByKeyword(keyword); }

    public List<Tag> findAll(){ return tagRepository.getAll(); }
}
