package com.main.acad.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private int maxPoolSize = 10;
    private int initialPoolSize = 1;
    private int currentPoolSize = 0;
    private String dbUrl = "/localhost:5432/javaBegin";
    private String dbUser = "postgres";
    private String dbPassword = "root";
    private List<Connection> pool = new ArrayList<>(maxPoolSize);

    public ConnectionPool() throws ClassNotFoundException, SQLException {
        initPooledConnections();
    }

    private void initPooledConnections() throws ClassNotFoundException, SQLException {
        for (int i = 0; i < initialPoolSize; i++) {
            openAndPoolConnection();
        }
    }

    private  void openAndPoolConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        pool.add(conn);
        currentPoolSize++;
    }

    public Connection borrowConnection() throws InterruptedException, SQLException {
        if ( currentPoolSize < maxPoolSize) {
            openAndPoolConnection();
        }
        return pool.get(currentPoolSize);
    }

    public void surrenderConnection(Connection conn) {
        currentPoolSize--;
        pool.remove(conn);
    }
}