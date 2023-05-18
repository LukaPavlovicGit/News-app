package com.example.newsapp.repository;

import com.example.newsapp.entities.Comment;

import java.util.List;

public interface CommentRepository {
    Comment insert(Comment comment);

    List<Comment> findByNewsId(Integer newsId);
}
