package com.main.acad.dao;

import com.main.acad.entity.User;
import com.main.acad.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleUserDao implements UserDao {

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());
    private static final String FIND_ALL = "SELECT * FROM users ORDER BY login";
    private static final String FIND_BY_USER = "SELECT * FROM users WHERE login=? AND password=?";
    private static final String EXIT_USER = "SELECT * FROM users WHERE login =?";
    private static final String CREATE_USER = "INSERT INTO users( login, password, role) VALUES (?, ?, ?);";
    private static Connection connection;
    private static SimpleUserDao instance;

    private SimpleUserDao() {
    }

    public static SimpleUserDao getInstance() {
        if (instance == null) {
            instance = new SimpleUserDao();
        }
        return instance;
    }

    public List<User> findAll() {
        PreparedStatement preparedStatement = null;
        List<User> users = new ArrayList<>();
        try {
            connection = connectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                Integer password = resultSet.getInt("password");
                String role = resultSet.getString("role");
                User user = User._myuserbuilder()
                        .login(login)
                        .password(password)
                        .role(role)
                        .build();
                users.add(user);
            }
        } catch (Exception e) {
            logger.info("An error occurred in the UserDao class in the findAll method");
            e.printStackTrace();
        }finally {
            connectionPool.surrenderConnection(connection);
        }
        logger.info("All users successfully find");
        return users;
    }

    public User findByUser(String login, Integer password) {

        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                login = resultSet.getString("login");
                password = resultSet.getInt("password");
                String role = resultSet.getString("role");
                User user = User._myuserbuilder()
                        .login(login)
                        .password(password)
                        .role(role)
                        .build();
                logger.info("All users successfully find");
                return user;
            } else {
                return null;
            }
        } catch (SQLException |InterruptedException e) {
            logger.info("An error occurred in the UserDao class in the findByUser method");
            e.printStackTrace();
        } finally {
            connectionPool.surrenderConnection(connection);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existUser(String userLogin) {

        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(EXIT_USER);
            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return false;
            }
            logger.info(userLogin + "is exist");
        } catch (Exception e) {
            logger.info("An error occurred in the UserDao class in the exitUser method");
            e.printStackTrace();
        } finally {
            connectionPool.surrenderConnection(connection);
        }
        return true;
    }

    @Override
    public boolean createNewUser(String login, Integer password, String role) {
        try {
            connection = connectionPool.borrowConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
            logger.info("New user successfully create");
            return true;
        } catch (Exception e) {
            logger.info("An error occurred in the UserDao class in the createNewUser method");
            e.printStackTrace();
        } finally {
            connectionPool.surrenderConnection(connection);
        }
        return false;
    }

}
