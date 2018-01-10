package com.main.acad.dao;

import com.main.acad.entity.Chapter;
import com.main.acad.util.ConnectionPool;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class ChapterDao implements Dao {

    private static final Logger logger = Logger.getLogger(Dao.class.getName());

    private String sqlInsert = "INSERT INTO chapters (name) VALUES(?)";
    private String sqlUpdate = "UPDATE chapters SET name =? WHERE id_chapter=?";
    private String sqlRemove = "DELETE  FROM chapters WHERE id_chapter=?";
   // private String sqlGetByid = "select c.name from chapters  c inner join \"references\" r on c.id_chapter =  r.id_refrence where r.id =?";//when we know id
    private String sqlGetByid = "select c.name from chapters c where c.id_chapter  in(select r.id_refrence from chapters c inner join \"references\" r on  r.id = c.id_chapter where c.name = ?)";//when we know name
    private String sqlGetAll = "SELECT * FROM \"chapters\" c INNER JOIN \"references\" r ON r.id_chapter = c.id_chapter where r.id = r.id_chapter";
    private String sqlGetChild = "select name from chapters where id_chapter IN (select r.id from chapters c inner join \"references\" r ON r.id_chapter = c.id_chapter where r.id_chapter = ?  and r.id != ?)";

    @Override
    public void addChapter(Chapter chapter) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
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
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
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
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
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
    public String getInformstioAboutChildren(String name) {
        ArrayList <Character> arrayList = new ArrayList<>();
        String s = "";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetByid)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FileReader reader = new FileReader(resultSet.getString("name"));
                int c;
                while ((c = reader.read()) != -1) {

                    s+=((char) c);
                }

            }
        } catch (SQLException e) {
            logger.info("connection have some error");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public List<Chapter> listChapters() {
        List<Chapter> chaptersList = new ArrayList();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
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
    public List<Chapter> listChildren(int id) {
        List<Chapter> chaptersList = new ArrayList();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetChild);
            {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Chapter chapter = new Chapter();
                    chapter.setName(resultSet.getString("name"));
                    chaptersList.add(chapter);
                }
                return chaptersList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chaptersList;
    }

    public static void main(String[] args) {
        ChapterDao chapter = new ChapterDao();
        System.out.println(chapter.getInformstioAboutChildren("Git push & pull"));
    }
}

