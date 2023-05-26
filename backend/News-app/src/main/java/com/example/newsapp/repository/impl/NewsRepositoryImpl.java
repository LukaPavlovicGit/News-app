package com.example.newsapp.repository.impl;

import com.example.newsapp.entities.*;
import com.example.newsapp.repository.*;
import com.example.newsapp.repository.mysqlAbstract.MySqlAbstractRepository;
import com.example.newsapp.utility.Utility;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsRepositoryImpl extends MySqlAbstractRepository implements NewsRepository {

    @Inject
    private TagRepository tagRepository;
    @Inject
    private NewsTagRepository newsTagRepository;
    private CommentRepository commentRepository;

    @Override
    public News insert(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO news (category_name, title, content, author, createdAt, visits, tags) VALUES(?, ?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getCategoryName());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setString(3, news.getContent());
            preparedStatement.setString(4, news.getAuthor());
            preparedStatement.setLong(5, news.getCreatedAt());
            preparedStatement.setInt(6, news.getVisits());
            preparedStatement.setString(7, news.getTags());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                news.setId(resultSet.getInt(1));

                if(Utility.notNullAndEmpty(news.getTags())){
                    String[] keywords = news.getTags().split("[,#.|;:-]");
                    for(String keyword : keywords){
                        Tag tag = tagRepository.findByKeyword(keyword);
                        if(tag == null){
                            tag = tagRepository.insert(new Tag(keyword));
                        }
                        newsTagRepository.insert(new NewsTag(news.getId(), tag.getId()));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return news.getId() == null ? null : news;
    }

    @Override
    public News update(News updatedNews) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE news SET ");
        if(Utility.notNullAndEmpty(updatedNews.getCategoryName())) {
            sb.append(" category_name=?,");
            indexes.put("category_name", idx++);
        }
        if(Utility.notNullAndEmpty(updatedNews.getTitle())) {
            sb.append(" title=?,");
            indexes.put("title", idx++);
        }
        if(Utility.notNullAndEmpty(updatedNews.getContent())) {
            sb.append(" content=?,");
            indexes.put("content", idx++);
        }
        if(Utility.notNullAndEmpty(updatedNews.getAuthor())) {
            sb.append(" author=?,");
            indexes.put("author", idx++);
        }
        if(Utility.notNullAndEmpty(updatedNews.getTags())) {
            sb.append(" tags=?,");
            indexes.put("tags", idx++);
        }

        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");
        indexes.put("id", idx);
        try {
            if(sb.toString().equals( "UPDATE news SET WHERE id=?")){
                throw new SQLException("Nothing to update...");
            }

            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            System.out.println("QUERY: " + sb);
            preparedStatement = connection.prepareStatement(sb.toString(), generatedColumns);

            if(sb.toString().contains("category_name=?")) { preparedStatement.setString(indexes.get("category_name"), updatedNews.getCategoryName()); }
            if(sb.toString().contains("title=?")) { preparedStatement.setString(indexes.get("title"), updatedNews.getTitle()); }
            if(sb.toString().contains("content=?")) { preparedStatement.setString(indexes.get("content"), updatedNews.getContent()); }
            if(sb.toString().contains("author=?")) { preparedStatement.setString(indexes.get("author"), updatedNews.getAuthor()); }
            if(sb.toString().contains("tags=?")) { preparedStatement.setString(indexes.get("tags"), updatedNews.getTags()); }
            preparedStatement.setInt(indexes.get("id"), updatedNews.getId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                if(Utility.notNullAndEmpty(updatedNews.getTags())){
                    newsTagRepository.deleteByNewsId(updatedNews.getId());
                    String[] keywords = updatedNews.getTags().split("[,#.|;:-]");
                    for(String keyword : keywords){
                        Tag tag = tagRepository.findByKeyword(keyword);
                        if(tag == null){
                            tag = tagRepository.insert(new Tag(keyword));
                        }
                        newsTagRepository.insert(new NewsTag(updatedNews.getId(), tag.getId()));
                    }
                }
            }
            else{
                updatedNews = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return updatedNews;
    }

    @Override
    public News newsVisited(Integer newsId) {
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id", "category_name", "title", "content", "author", "created_at", "visits", "tags"};
            preparedStatement = connection.prepareStatement("UPDATE news SET visits = visits + 1 WHERE id = ?", generatedColumns);
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getLong("created_at"),
                        resultSet.getInt("visits"),
                        resultSet.getString("tags") );

                List<Comment> comments = commentRepository.findByNewsId(newsId);
                news.setComments(comments);
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
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id", "category_name", "title", "content", "author", "created_at", "visits", "tags"};
            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id = ?", generatedColumns);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getLong("created_at"),
                        resultSet.getInt("visits"),
                        resultSet.getString("tags"));

                newsTagRepository.deleteByNewsId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> mostRead() {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM news ORDER BY visits DESC LIMIT 10");

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
    public List<News> findAll(int page) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news ORDER BY created_at DESC LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
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

    @Override
    public List<News> findAllByCategory(String categoryName) {
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

    @Override
    public List<News> findAllByTag(String tagName) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE tags LIKE ?");
            String likePattern = "%" + tagName + "%";
            preparedStatement.setString(1, likePattern);
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
