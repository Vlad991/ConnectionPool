package com.infopulse.model;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBPoolCache extends DBPool {
    private PGPoolingDataSource source;
    private static final int MAX_CONNECTIONS = 20;
    private static final int MAX_INITIAL_CONNECTIONS = 20;

    DBPoolCache(String host, String database, String user, String password) {
        source = new PGPoolingDataSource();
        source.setDataSourceName("A Data Source");
        source.setServerName(host);
        source.setDatabaseName(database);
        source.setUser(user);
        source.setPassword(password);
        source.setMaxConnections(MAX_CONNECTIONS);
        source.setInitialConnections(MAX_INITIAL_CONNECTIONS);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void putConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
