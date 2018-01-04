package com.main.acad.util;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPool {
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:postgresql://localhost:5432/javaBegin";
    private static final String USER = "postgres";
    private static final String DRIVER = "org.postgresql.Driver";

    private static BasicDataSource dataSource;

    static {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(DRIVER);
            dataSource.setUrl(URL);
            dataSource.setUsername(USER);
            dataSource.setPassword(PASSWORD);

            dataSource.setMinIdle(1);
            dataSource.setMaxIdle(5);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
