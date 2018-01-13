package com.main.acad.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ConnectionPool {

    private static ConnectionPool instance;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());
    private BlockingQueue<Connection> pool;
    private int maxPoolSize;
    private int currentPoolSize;
    private String dataBaseUrl;
    private String dataBaseUser;
    private String dataBasePassword;
    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private static InputStream inputStream = classLoader.getResourceAsStream("config.properties");
    private static Properties properties = new Properties();

    private ConnectionPool() {
        this.maxPoolSize = 10;
        this.pool = new LinkedBlockingQueue<>(maxPoolSize);
        this.currentPoolSize = 0;
        this.dataBaseUrl = properties.getProperty("url");
        this.dataBaseUser = properties.getProperty("login");
        this.dataBasePassword = properties.getProperty("password");
        try {
            Class.forName(properties.getProperty("driver"));
            openAndPoolConnection();
        } catch (ClassNotFoundException e) {
            logger.info("An error occurred in ConnectionPool class with private Constructore");
            e.printStackTrace();
        }
    }

    static {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.info("An error occurred in ConnectionPool class with config.properties file");
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void openAndPoolConnection() {
        try {
            Connection conn = DriverManager.getConnection(dataBaseUrl, dataBaseUser, dataBasePassword);
            pool.offer(conn);
            currentPoolSize++;
        } catch (SQLException e) {
            logger.info("An error occurred in ConnectionPool class with openAndPoolConnection method");
            e.printStackTrace();
        }
    }

    public Connection borrowConnection() throws InterruptedException {
        if (pool.peek() == null && currentPoolSize < maxPoolSize) {
            openAndPoolConnection();
        }
        logger.info("Connecton successfully take in queue");
        return pool.take();
    }

    public void surrenderConnection(Connection conn) {
        logger.info("Connecton successfully remove in queue");
        pool.offer(conn);
    }
}