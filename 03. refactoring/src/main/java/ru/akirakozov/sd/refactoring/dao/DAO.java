package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.utils.SQLOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Function;

public abstract class DAO {
    private <T, R> R executeOnStatement(SQLOperation<T> query, Function<T, R> resultMapper) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                R result = resultMapper.apply(query.executeOn(stmt));
                stmt.close();
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected int executeUpdate(String sql) {
        return executeOnStatement((stmt) -> stmt.executeUpdate(sql), Function.identity());
    }

    protected <R> R executeQuery(String sql, Function<ResultSet, R> resultMapper) {
        return executeOnStatement((stmt) -> stmt.executeQuery(sql), resultMapper);
    }
}
