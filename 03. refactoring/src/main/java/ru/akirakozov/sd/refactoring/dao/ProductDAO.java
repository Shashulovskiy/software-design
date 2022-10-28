package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProductDAO extends DAO {
    public int addProduct(String name, Long price) {
        String sql = String.format("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"%s\",%d)", name, price);

        return executeUpdate(sql);
    }

    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM PRODUCT";

        return executeQuery(sql, this::fetchAll);
    }

    public Product findProductWithMaxPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";

        return executeQuery(sql, this::fetchProduct);
    }

    public Product findProductWithMinPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE ASC LIMIT 1";

        return executeQuery(sql, this::fetchProduct);
    }

    public Integer findTotalSum() {
        String sql = "SELECT SUM(price) FROM PRODUCT";

        return executeQuery(sql, this::fetchInt);
    }

    public Integer findTotalCount() {
        String sql = "SELECT COUNT(*) FROM PRODUCT";

        return executeQuery(sql, this::fetchInt);
    }

    private Integer fetchInt(ResultSet rs) {
        return fetchProduct(rs, (result) -> {
            try {
                return rs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Product fetchProduct(ResultSet rs) {
        return fetchProduct(rs, (result) -> {
            try {
                String name = result.getString("name");
                int price = result.getInt("price");
                return new Product(name, price);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T fetchProduct(ResultSet rs, Function<ResultSet, T> mapper) {
        try {
            if (rs.next()) {
                return mapper.apply(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> fetchAll(ResultSet rs) {
        List<Product> products = new ArrayList<>();
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                products.add(new Product(name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
