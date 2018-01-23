package com.main.acad.dao;

import com.main.acad.entity.Chapter;
import com.main.acad.error.ChapterDaoFailedExeption;
import com.main.acad.util.ConnectionPool;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleChapterDao implements ChapterDao {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(ChapterDao.class.getName());
    private static final String INSERT_CHAPTER = "INSERT INTO chapters (name) VALUES(?)";
    private static final String UPDATE_CHAPTER = "UPDATE chapters SET name =? WHERE id_chapter=?";
    private static final String REMOVE_CHAPTER = "DELETE  FROM chapters WHERE id_chapter=?";
    private static final String GET_ALL_CHILDREN_BY_NAME = "SELECT c.name FROM chapters c WHERE c.id_chapter  IN(SELECT r.id_refrence FROM chapters c INNER JOIN \"references\" r ON  r.id = c.id_chapter WHERE c.name = ?)";
    private static final String GET_ALL_CHAPTERS = "SELECT * FROM \"chapters\" c INNER JOIN \"references\" r ON r.id_chapter = c.id_chapter where r.id = r.id_chapter";
    private static final String GET_CHILDREN_INFORMATION_BY_ID = "SELECT name FROM chapters WHERE id_chapter IN (SELECT r.id FROM chapters c INNER JOIN \"references\" r ON r.id_chapter = c.id_chapter WHERE r.id_chapter = ?  AND r.id != ?)";
    private static final String GET_ID = "SELECT id_chapter FROM chapters WHERE name =?";
    private static final String INSERT_INTO_REFERENCES = "INSERT INTO \"references\" ( id, id_chapter, id_refrence) VALUES (?, ?, ?)";
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
    public void addChapter(String name) {
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHAPTER);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            logger.info("Chapter successfully saved. Chapter details: ");
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the addChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }

    @Override
    public void updateChapter(Chapter chapter) {
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CHAPTER);
            preparedStatement.setString(1, chapter.getName());
            preparedStatement.setInt(2, chapter.getId());
            preparedStatement.executeUpdate();
            logger.info("Chapter successfully update. Chapter details: " + chapter);
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the updateChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }

    @Override
    public void removeChapter(int id) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CHAPTER);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("Chapter successfully remove. Chapter id: " + chapter.getId());
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the updateChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }

    @Override
    public List<Chapter> getlistChapters() {
        List<Chapter> chaptersList = new ArrayList<>();
        try {
            connection = connectionPool.borrowConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_CHAPTERS);
            while (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(resultSet.getInt("id_chapter"));
                chapter.setName(resultSet.getString("name"));
                chaptersList.add(chapter);
            }
            logger.info("All Chapters successfully get. List chapters details : " + chaptersList.size());
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the getlistChapters method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
        return chaptersList;
    }

    @Override
    public FileReader getInformstioAboutChildren(String name) {
        FileReader fileReader = null;
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CHILDREN_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fileReader = new FileReader(resultSet.getString("name"));
            }
            logger.info("All information about chapter child successfully get.");
            return fileReader;
        } catch (SQLException | IOException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the getInformstioAboutChildren method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }

    @Override
    public List<Chapter> getlistChildren(int id) {
        List<Chapter> chaptersList = new ArrayList<>();
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CHILDREN_INFORMATION_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setName(resultSet.getString("name"));
                chaptersList.add(chapter);
            }
            logger.info("All information about list childChapters successfully get.List details :" + chaptersList);
            return chaptersList;
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the getlistChildren method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }

    @Override
    public boolean createNewChildChapter(String chapterName, String nameFile, String chapterText, String nameSubChapters) {
        try {
            File directoryFile = new File("D:\\IT\\JavaBeginFiles\\" + chapterName + "\\" + nameFile.trim());
            FileOutputStream fileOutputStream = new FileOutputStream(directoryFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            Writer writeFile = new BufferedWriter(outputStreamWriter);
            writeFile.write(chapterText.trim());
            writeFile.close();
            addChapter(String.valueOf(directoryFile));
            addChapter(nameSubChapters);
            addRowIntoReferences( selectID(nameSubChapters), selectID(chapterName),selectID(String.valueOf(directoryFile)));
            logger.info("Create new subChapter successfully ");
            return true;
        } catch (IOException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the createNewChildChapter method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        }
    }

    private Integer selectID(String name) {
        Integer result = 0;
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ID);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                result = resultSet.getInt(1);
            logger.info("Chapter successfully saved");
            return result;
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the selectID method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }

    private void addRowIntoReferences( Integer subChapterName, Integer chapterName,Integer dirChaptersChild) {
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_REFERENCES);
            preparedStatement.setInt(1, subChapterName);
            preparedStatement.setInt(2, chapterName);
            preparedStatement.setInt(3, dirChaptersChild);
            preparedStatement.executeUpdate();
            logger.info("Chapter successfully saved.");
        } catch (SQLException | InterruptedException e) {
            logger.info("An error occurred in the SimpleChapterDao class in the addRowIntoReferences method" + e.getMessage());
            throw new ChapterDaoFailedExeption(e.getMessage());
        } finally {
            connectionPool.surrenderConnection(connection);
        }
    }
}

