package com.example.newsapp.repository;

import com.example.newsapp.entities.User;

import java.util.List;

public interface UserRepository {

    User insert(User user);
    User getById(Integer id);
    User update(User user);
    void statusActivation(Integer userId);
    void statusDeactivation(Integer userId);
    List<User> getAll(Integer page);
    User findByEmail(String email);
}
