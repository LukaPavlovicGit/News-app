package com.example.newsapp.repository;

import com.example.newsapp.entities.User;

import java.util.List;

public interface UserRepository {

    User insert(User user);
    User update(User user);
    List<User> getAll();
    User findByEmail(String email);
}
