package com.example.newsapp.repository;

import com.example.newsapp.entities.User;

public interface UserRepository {

    Boolean insert(User user);
    User update(User user);
    User findByEmail(String email);
}
