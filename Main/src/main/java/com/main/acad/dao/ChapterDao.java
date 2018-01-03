package com.main.acad.dao;

import com.main.acad.entity.Chapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ChapterDao implements Dao {

    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:postgresql://localhost:5432/javaBegin";
    private static final String USER = "postgres";
    private static final Logger logger = Logger.getLogger(Dao.class.getName());

    private String sqlInsert = "INSERT INTO chapters (name) VALUES(?)";
    private String sqlUpdate = "UPDATE chapters SET name =? WHERE id_chapter=?";
    private String sqlRemove = "DELETE  FROM chapters WHERE id_chapter=?";
    private String sqlGetByid = "SELECT r.id_chapter, r.id_refrence, c.name FROM \"references\" r INNER JOIN chapters c ON r.id_refrence = c.id_chapter where c.id_chapter = ?";
    private String sqlGetAll = "SELECT * FROM \"chapters\" c INNER JOIN \"references\" r ON r.id_chapter = c.id_chapter where r.id = r.id_chapter";
    private String sqlGetChild = "select name from chapters where id_chapter IN (select r.id from chapters c inner join \"references\" r ON r.id_chapter = c.id_chapter where r.id_chapter = ?  and r.id != ?)";

    @Override
    public void addChapter(Chapter chapter) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, chapter.getName());
            preparedStatement.executeUpdate();
            logger.info("User successfully saved. User details: " + chapter);
        } catch (SQLException e) {
            logger.info("connection have some error");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateChapter(Chapter chapter) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, chapter.getName());
            preparedStatement.setInt(2, chapter.getId());
            preparedStatement.executeUpdate();
            logger.info("User successfully update. User details: " + chapter);
        } catch (SQLException e) {
            logger.info("connection have some error");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChapter(int id) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRemove)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("User successfully remove. User details: " + chapter);
        } catch (SQLException e) {
            logger.info("connection have some error");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Chapter getChapter(int id) {
        Chapter chapter = new Chapter();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetByid)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chapter.setId(resultSet.getInt(1));
                chapter.setName(resultSet.getString(2));
            }
            logger.info("User successfully get. User details: " + chapter);
        } catch (SQLException e) {
            logger.info("connection have some error");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Chapter> listChapters() {
        List<Chapter> chaptersList = new ArrayList();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlGetAll);) {
            while (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(resultSet.getInt("id_chapter"));
                chapter.setName(resultSet.getString("name"));
                chaptersList.add(chapter);
            }
            logger.info("All Chapters successfully get. Chapters details: " + chaptersList.size());

        } catch (SQLException e) {
            logger.info("connection have some error");
        }
        return chaptersList;
    }

    @Override
    public List listChildren(int id) {
        List<String> chaptersList = new ArrayList();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetChild);
            {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Chapter chapter = new Chapter();
                    chapter.setName(resultSet.getString("name"));
                    chaptersList.add(chapter.getName());
                }
                return chaptersList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chaptersList;
    }
}