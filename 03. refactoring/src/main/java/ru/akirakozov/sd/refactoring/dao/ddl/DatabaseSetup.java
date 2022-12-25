package ru.akirakozov.sd.refactoring.dao.ddl;

import ru.akirakozov.sd.refactoring.dao.DAO;

import java.util.function.Function;

public class DatabaseSetup extends DAO {
    public void setupAll() {
        createTableProducts();
    }

    private void createTableProducts() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";

        executeQuery(sql, Function.identity());
    }
}
