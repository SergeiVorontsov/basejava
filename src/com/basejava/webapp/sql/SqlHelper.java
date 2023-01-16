package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(String sqlRequest) {
        execute(sqlRequest, PreparedStatement::execute);
    }

    public <T> T execute(String sqlRequest, Executor<T> statement) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            return statement.execute(ps);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(e);
            } else {
                throw new StorageException(e);
            }
        }
    }
}