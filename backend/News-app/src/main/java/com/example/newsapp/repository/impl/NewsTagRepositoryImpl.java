package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.Comment;
import com.example.newsapp.entities.NewsTag;
import com.example.newsapp.repository.CategoryRepository;
import com.example.newsapp.repository.NewsTagRepository;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsTagRepositoryImpl extends MySqlAbstractRepository implements NewsTagRepository {

    @Override
    public NewsTag insert(NewsTag newsTag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO newstag (news_id, tag_id) VALUES(?, ?)", generatedColumns);
            preparedStatement.setInt(1, newsTag.getNewsId());
            preparedStatement.setInt(2, newsTag.getTagId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                newsTag.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return newsTag;
    }

    @Override
    public List<NewsTag> findAllByNewsId(Integer newsId) {
        List<NewsTag> newsTags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM newstag WHERE news_id = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                newsTags.add(new NewsTag(
                        resultSet.getInt("id"),
                        resultSet.getInt("news_id"),
                        resultSet.getInt("tag_id")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTags;
    }

    @Override
    public List<NewsTag> findAllByTagId(Integer tagId) {
        List<NewsTag> newsTags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM newstag WHERE tag_id = ?");
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                newsTags.add(new NewsTag(
                        resultSet.getInt("id"),
                        resultSet.getInt("news_id"),
                        resultSet.getInt("tag_id")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTags;
    }
}
