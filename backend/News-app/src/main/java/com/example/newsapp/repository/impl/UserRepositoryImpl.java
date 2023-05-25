package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.Category;
import com.example.newsapp.entities.Tag;
import com.example.newsapp.entities.User;
import com.example.newsapp.repository.CategoryRepository;
import com.example.newsapp.repository.UserRepository;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;
import com.example.newsapp.utility.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl extends MySqlAbstractRepository implements UserRepository {
    @Override
    public User insert(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (role, firstname, lastname, email, hashed_password, status) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
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
            System.out.println("EXCEPTION: " +  e.getClass());
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

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE users SET ");
        if(Utility.notNullAndEmpty(user.getRole())) {
            sb.append(" role=?,");
            indexes.put("role", idx++);
        }
        if(Utility.notNullAndEmpty(user.getFirstname())) {
            sb.append(" firstname=?,");
            indexes.put("firstname", idx++);
        }
        if(Utility.notNullAndEmpty(user.getLastname())) {
            sb.append(" lastname=?,");
            indexes.put("lastname", idx++);
        }
        if(Utility.notNullAndEmpty(user.getEmail())) {
            sb.append(" email=?,");
            indexes.put("email", idx++);
        }
        if(Utility.notNullAndEmpty(user.getHashedPassword())) {
            sb.append(" hashed_password=?,");
            indexes.put("hashed_password", idx++);
        }
        if(user.getStatus() != null) {
            sb.append(" status=?,");
            indexes.put("status", idx++);
        }

        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");

        indexes.put("id", idx++);
        try {
            if(sb.toString().equals( "UPDATE users SET WHERE id=?")){
                throw new SQLException("Nothing to update...");
            }

            connection = this.newConnection();

            System.out.println("QUERY: " + sb);
            preparedStatement = connection.prepareStatement(sb.toString());

            if(sb.toString().contains("role=?")) { preparedStatement.setString(indexes.get("role"), user.getRole()); }
            if(sb.toString().contains("firstname=?")) { preparedStatement.setString(indexes.get("firstname"), user.getFirstname()); }
            if(sb.toString().contains("lastname=?")) { preparedStatement.setString(indexes.get("lastname"), user.getLastname()); }
            if(sb.toString().contains("email=?")) { preparedStatement.setString(indexes.get("email"), user.getEmail()); }
            if(sb.toString().contains("hashed_password=?")) { preparedStatement.setString(indexes.get("hashed_password"), user.getHashedPassword()); }
            if(sb.toString().contains("status=?")) { preparedStatement.setBoolean(indexes.get("status"), user.getStatus()); }
            preparedStatement.setInt(indexes.get("id"), user.getId());

            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void statusActivation(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET status=? WHERE id=?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void statusDeactivation(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET status=? WHERE id=?");
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
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
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
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
