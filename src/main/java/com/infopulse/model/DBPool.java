package com.infopulse.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBPool {
    private String url, user, password;

    DBPool(String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName("org.postgresql.Driver");
    }

    public DBPool() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void putConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
