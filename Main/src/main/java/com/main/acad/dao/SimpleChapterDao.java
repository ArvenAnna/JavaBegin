package com.main.acad.dao;

import com.main.acad.entity.Chapter;
import com.main.acad.error.ChapterDaoFailedExeption;
import com.main.acad.util.ConnectionPool;

import java.io.IOException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleChapterDao implements ChapterDao {
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final Logger logger = Logger.getLogger(ChapterDao.class.getName());

    private static String sqlInsertQuery = "INSERT INTO chapters (name) VALUES(?)";
    private static String sqlUpdateQuery = "UPDATE chapters SET name =? WHERE id_chapter=?";
    private static String sqlRemoveQuery = "DELETE  FROM chapters WHERE id_chapter=?";
    private static String sqlGetByidQuery = "SELECT c.name FROM chapters c WHERE c.id_chapter  IN(SELECT r.id_refrence FROM chapters c INNER JOIN \"references\" r ON  r.id = c.id_chapter WHERE c.name = ?)";
    private static String sqlGetAllQuery = "SELECT * FROM \"chapters\" c INNER JOIN \"references\" r ON r.id_chapter = c.id_chapter where r.id = r.id_chapter";
    private static String sqlGetChildQuery = "SELECT name FROM chapters WHERE id_chapter IN (SELECT r.id FROM chapters c INNER JOIN \"references\" r ON r.id_chapter = c.id_chapter WHERE r.id_chapter = ?  AND r.id != ?)";
    private static Connection connection;
    private static SimpleChapterDao instance;

    private SimpleChapterDao() {
    }

    public static SimpleChapterDao getInstance() {
        if (instance == null) {
            instance = new SimpleChapterDao();
        }
        return instance;
    }

    @Override
    public void addChapter(Chapter chapter) {
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertQuery);
            preparedStatement.setString(1, chapter.getName());
            preparedStatement.executeUpdate();
            connectionPool.surrenderConnection(connection);
            logger.info("Chapter successfully saved. Chapter details: " + chapter);
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the ChapterDao class in the addChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
    }

    @Override
    public void updateChapter(Chapter chapter) {
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateQuery);
            preparedStatement.setString(1, chapter.getName());
            preparedStatement.setInt(2, chapter.getId());
            preparedStatement.executeUpdate();
            connectionPool.surrenderConnection(connection);
            logger.info("Chapter successfully update. Chapter details: " + chapter);
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the ChapterDao class in the updateChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
    }

    @Override
    public void removeChapter(int id) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connectionPool.surrenderConnection(connection);
            logger.info("Chapter successfully remove. Chapter id: " + chapter.getId());
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the ChapterDao class in the updateChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
    }

    @Override
    public List<Chapter> getlistChapters() {
        List<Chapter> chaptersList = new ArrayList<>();
        try {
            connection = connectionPool.borrowConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetAllQuery);
            while (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(resultSet.getInt("id_chapter"));
                chapter.setName(resultSet.getString("name"));
                chaptersList.add(chapter);
            }
            connectionPool.surrenderConnection(connection);
            logger.info("All Chapters successfully get. List chapters details : " + chaptersList.size());
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the ChapterDao class in the getlistChapters method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
        return chaptersList;
    }

    @Override
    public FileReader getInformstioAboutChildren(String name) {
        FileReader fileReader = null;
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetByidQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fileReader = new FileReader(resultSet.getString("name"));
            }
            connectionPool.surrenderConnection(connection);
            logger.info("All information about chapter child successfully get.");
            return fileReader;
        } catch (SQLException | IOException | InterruptedException e) {
            logger.info("An error occurred in the ChapterDao class in the getInformstioAboutChildren method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
    }

    @Override
    public List<Chapter> getlistChildren(int id) {
        List<Chapter> chaptersList = new ArrayList<>();
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetChildQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setName(resultSet.getString("name"));
                chaptersList.add(chapter);
            }
            connectionPool.surrenderConnection(connection);
            logger.info("All information about list childChapters successfully get.List details :" + chaptersList);
            return chaptersList;
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the ChapterDao class in the getlistChildren method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
    }
}

