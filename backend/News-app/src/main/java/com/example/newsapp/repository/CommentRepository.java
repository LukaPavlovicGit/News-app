package com.example.newsapp.repository;

import com.example.newsapp.entities.Comment;

import java.util.List;

public interface CommentRepository {
    Comment insert(Comment comment);
    Comment update(Comment comment);
    Comment delete(Integer id);
    List<Comment> findByNewsId(Integer newsId);
}
