package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.Category;
import com.example.newsapp.entities.User;
import com.example.newsapp.repository.CategoryRepository;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;
import com.example.newsapp.utility.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepositoryImpl extends MySqlAbstractRepository implements CategoryRepository {

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories");
            while(resultSet.next()){
                categories.add(new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category insert(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO categories (name, description) VALUES(?, ?)", generatedColumns);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                category.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return category.getId() == null ? null : category;
    }

    @Override
    public Category update(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE categories SET ");
        if(Utility.notNullAndEmpty(category.getName())) {
            sb.append(" name=?,");
            indexes.put("name", idx++);
        }
        if(Utility.notNullAndEmpty(category.getDescription())) {
            sb.append(" description=?,");
            indexes.put("description", idx++);
        }
        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");

        indexes.put("id", idx);

        try {
            if (sb.toString().equals("UPDATE categories SET WHERE id=?")) {
                throw new SQLException("Nothing to update...");
            }

            connection = this.newConnection();

            System.out.println("QUERY: " + sb);
            preparedStatement = connection.prepareStatement(sb.toString());

            if(sb.toString().contains("name=?")) { preparedStatement.setString(indexes.get("name"), category.getName()); }
            if(sb.toString().contains("description=?")) { preparedStatement.setString(indexes.get("description"), category.getDescription()); }
            preparedStatement.setInt(indexes.get("id"), category.getId());

            int status = preparedStatement.executeUpdate();
            if(status == 0){
                category = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public Category delete(Integer id) {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM news WHERE category_name = (SELECT name FROM categories WHERE id=?)");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int count = resultSet.getInt("total");
            System.out.println("COUNT: " + count);
            if (count > 0)
                throw new Exception();

            closeStatement(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            preparedStatement.setInt(1, id);
            int status = preparedStatement.executeUpdate();

            if(status == 1){
                category = new Category(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

//    public Category findByName(String name) {
//        Category category = new Category();
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = this.newConnection();
//            preparedStatement = connection.prepareStatement("SELECT * FROM categories WHERE name = ?");
//            preparedStatement.setString(1, name);
//            resultSet = preparedStatement.executeQuery();
//
//            if(resultSet.next()){
//                category.setId(resultSet.getInt("id"));
//                category.setName(resultSet.getString("name"));
//                category.setDescription(resultSet.getString("description"));
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            this.closeStatement(preparedStatement);
//            this.closeResultSet(resultSet);
//            this.closeConnection(connection);
//        }
//
//        return category;
//    }
}
