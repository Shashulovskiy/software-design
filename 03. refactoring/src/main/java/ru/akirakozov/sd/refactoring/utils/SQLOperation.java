package ru.akirakozov.sd.refactoring.utils;

import java.sql.SQLException;
import java.sql.Statement;

@FunctionalInterface
public interface SQLOperation<T> {
    public T executeOn(Statement statement) throws SQLException;
}
