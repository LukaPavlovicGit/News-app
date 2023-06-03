package com.example.newsapp.repository;

import com.example.newsapp.entities.User;
import com.example.newsapp.exceptions.UniqueEmailException;

import java.util.List;

public interface UserRepository {

    User insert(User user) throws UniqueEmailException;
    User getById(Integer id);
    User update(User user);
    void statusActivation(Integer userId);
    void statusDeactivation(Integer userId);
    List<User> getAll(Integer page);
    User findByEmail(String email);
}
