package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.*;
import com.example.newsapp.repository.CategoryRepository;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.repository.NewsTagRepository;
import com.example.newsapp.repository.TagRepository;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsRepositoryImpl extends MySqlAbstractRepository implements NewsRepository {

    @Inject
    private TagRepository tagRepository;
    @Inject
    private NewsTagRepository newsTagRepository;

    @Override
    public News insert(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO news (category_name, title, content, author, createdAt, visits) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getCategoryName());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setString(3, news.getContent());
            preparedStatement.setString(4, news.getAuthor());
            preparedStatement.setLong(5, news.getCreatedAt());
            preparedStatement.setInt(6, news.getVisits());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                news.setId(resultSet.getInt(1));

                if(news.getTags() != null && !news.getTags().isEmpty()){
//                    for(Tag tag : news.getTags()){
//
//                        tag = tagRepository.findByKeyword(tag.getKeyword());
//                        if(tag == null){
//                            tag = tagRepository.insert(tag);
//                        }
//
//                        newsTagRepository.insert(new NewsTag(news.getId(), tag.getId()));
//                    }

                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return news;
    }

    @Override
    public News delete(Integer id) {
        return null;
    }

    @Override
    public News findById(Integer id) {
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                news = new News();
                news.setId(resultSet.getInt("id"));
                news.setCategoryName(resultSet.getString("category_name"));
                news.setTitle(resultSet.getString("title"));
                news.setContent(resultSet.getString("content"));
                news.setAuthor(resultSet.getString("author"));
                news.setCreatedAt(resultSet.getLong("createdAt"));
                news.setVisits(resultSet.getInt("visits"));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> findAll() {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM news");

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visits")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> findAllCategory(String categoryName) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE category_name = ?");
            preparedStatement.setString(1, categoryName);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visits")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }
}
