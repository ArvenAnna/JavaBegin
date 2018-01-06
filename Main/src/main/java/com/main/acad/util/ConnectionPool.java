package com.main.acad.util;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    private static BasicDataSource dataSource;

    static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    static InputStream input = classLoader.getResourceAsStream("config.properties");
    static Properties prop = new Properties();

    static {
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(prop.getProperty("driver"));
        dataSource.setUrl(prop.getProperty("url"));
        dataSource.setUsername(prop.getProperty("login"));
        dataSource.setPassword(prop.getProperty("password"));

        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(5);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}