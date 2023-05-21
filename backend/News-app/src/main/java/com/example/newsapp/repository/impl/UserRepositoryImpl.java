package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.Category;
import com.example.newsapp.entities.Tag;
import com.example.newsapp.entities.User;
import com.example.newsapp.repository.CategoryRepository;
import com.example.newsapp.repository.UserRepository;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends MySqlAbstractRepository implements UserRepository {
    @Override
    public User insert(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (role, firstname, lastname, email, hashedPassword, status) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getRole());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getHashedPassword());
            preparedStatement.setBoolean(6, user.getStatus());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return user.getId() != null ? user : null;
    }

    @Override
    public User update(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("UPDATE INTO users (role, firstname, lastname, email, hashed_password, status) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);

            if(user.getRole() != null && !user.getRole().isEmpty()){
                preparedStatement.setString(1, user.getRole());
            }
            if(user.getFirstname() != null && !user.getFirstname().isEmpty()){
                preparedStatement.setString(2, user.getFirstname());
            }
            if(user.getLastname() != null && !user.getLastname().isEmpty()) {
                preparedStatement.setString(3, user.getLastname());
            }
            if(user.getEmail() != null && !user.getEmail().isEmpty()) {
                preparedStatement.setString(4, user.getEmail());
            }
            if(user.getHashedPassword() != null && !user.getHashedPassword().isEmpty()) {
                preparedStatement.setString(5, user.getHashedPassword());
            }
            preparedStatement.setBoolean(6, user.getStatus());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return user;
    }

    @Override
    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()){
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("role"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("status")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User findByEmail(String email) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setRole(resultSet.getString("role"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setHashedPassword(resultSet.getString("hashed_password"));
                user.setStatus(resultSet.getBoolean("status"));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }
}
