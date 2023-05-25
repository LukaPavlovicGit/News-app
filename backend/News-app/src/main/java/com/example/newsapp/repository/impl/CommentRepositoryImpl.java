package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.Category;
import com.example.newsapp.entities.Comment;
import com.example.newsapp.repository.CommentRepository;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;
import com.example.newsapp.utility.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentRepositoryImpl extends MySqlAbstractRepository implements CommentRepository {

    @Override
    public Comment insert(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (news_id, author, content, createdAt) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, comment.getNewsId());
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setLong(4, comment.getCreatedAt());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return comment.getId() == null ? null : comment;
    }

    @Override
    public Comment update(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE comments SET ");
        if(Utility.notNullAndEmpty(comment.getAuthor())) {
            sb.append(" author=?,");
            indexes.put("author", idx++);
        }
        if(Utility.notNullAndEmpty(comment.getContent())) {
            sb.append(" content=?,");
            indexes.put("content", idx++);
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
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(sb.toString(), generatedColumns);

            if(sb.toString().contains("author=?")) { preparedStatement.setString(indexes.get("author"), comment.getAuthor()); }
            if(sb.toString().contains("content=?")) { preparedStatement.setString(indexes.get("content"), comment.getContent()); }
            preparedStatement.setInt(indexes.get("id"), comment.getId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next() == false){
                comment = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return comment;
    }

    @Override
    public Comment delete(Integer id) {
        Comment comment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id", "news_id", "author", "content"};
            preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE id = ?", generatedColumns);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                comment = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("news_id"),
                        resultSet.getString("author"),
                        resultSet.getString("content")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> findByNewsId(Integer newsId) {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE news_id = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                comments.add(new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("news_id"),
                        resultSet.getString("author"),
                        resultSet.getString("content"),
                        resultSet.getLong("createdAt")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }
}
