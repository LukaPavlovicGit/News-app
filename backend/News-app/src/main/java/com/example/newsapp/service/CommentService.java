package com.example.newsapp.service;

import com.example.newsapp.entities.Comment;
import com.example.newsapp.repository.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;

    public Comment insert(Comment comment) { return commentRepository.insert(comment); }
    public List<Comment> findAllByNewsId(Integer newsId) { return commentRepository.findByNewsId(newsId); }
}
