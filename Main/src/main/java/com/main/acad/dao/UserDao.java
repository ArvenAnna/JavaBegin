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

public class UserDao implements UserDaoMethods{

    private static final String FIND_ALL = "SELECT * FROM users ORDER BY login";
    private static final String FIND_BY_USER = "SELECT * FROM users WHERE login=? AND password=?";
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
            e.printStackTrace();
        }
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        throw new UnsupportedOperationException();
    }

    public Connection getConnection() {
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
