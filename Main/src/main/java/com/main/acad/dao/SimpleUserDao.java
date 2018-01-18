package com.main.acad.dao;

import com.main.acad.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class SimpleUserDao implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());
    private static final String FIND_ALL = "SELECT * FROM users ORDER BY login";
    private static final String FIND_BY_USER = "SELECT * FROM users WHERE login=? AND password=?";
    private static final String EXIT_USER = "SELECT * FROM users WHERE login =?";
    private static final String CREATE_USER = "INSERT INTO users( login, password, role) VALUES (?, ?, ?);";
    private static String dataBaseUrl;
    private static String dataBaseUser;
    private static String dataBasePassword;

    public List<User> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<User> users = new ArrayList<User>();
        try {
            connection = getConnection();
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
        }
        logger.info("All users successfully find");
        return users;
    }

    public User findByUser(String login, Integer password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
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
            }else{
                return null;
            }
        } catch (SQLException e) {
            logger.info("An error occurred in the UserDao class in the findByUser method");
            e.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existUser(String userLogin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
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
        }
        return true;
    }

    @Override
    public boolean createNewUser(String login, Integer password, String role) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
            logger.info( "New user successfully create");
            return true;
        } catch (Exception e) {
            logger.info("An error occurred in the UserDao class in the createNewUser method");
            e.printStackTrace();
        }
        return false;
    }

    private Connection getConnection() {
        Connection connection = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            dataBaseUrl = properties.getProperty("url");
            dataBaseUser = properties.getProperty("login");
            dataBasePassword = properties.getProperty("password");
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(dataBaseUrl, dataBaseUser, dataBasePassword);
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
