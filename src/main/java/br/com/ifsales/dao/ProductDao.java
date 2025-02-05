package br.com.ifsales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import br.com.ifsales.model.Category;
import br.com.ifsales.model.Product;

public class ProductDao {
    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Product product) throws SQLException {
        String sql = "CALL IFSALES_PKG.INSERT_PRODUCT(?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getBrand());
            ps.setString(2, product.getModel());
            ps.setInt(3, product.getModelYear());
            ps.setDouble(4, product.getPrice());
            ps.setLong(5, product.getCategory().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while saving product to oracle sql");
        }

        return true;
    }

    public Optional<Product> getProductById(Long id) throws SQLException {
        Product product;

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            product = extractProductFromQuery(rs);
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(product);
    }

    public Optional<List<Product>> getProductsByBrand(String brand) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE BRAND = ?""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, brand);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByModel(String model) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE MODEL = ?""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, model);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByModelYear(int modelYear) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE MODEL_YEAR = ?""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, modelYear);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByPrice(Double price) throws SQLException, SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE PRICE = ?""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, price);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByCategoryId(Long categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE CATEGORY_ID = ?""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByTotalSales(int totalSales) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE TOTAL_SALES = ?""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, totalSales);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM PRODUCTS""";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                products.add(extractProductFromQuery(rs));
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving products in oracle sql");
        }

        return Optional.of(products);
    }

    public Boolean update(Product product) throws SQLException {
        String sql = """
                UPDATE PRODUCTS
                SET BRAND = ?,
                    MODEL = ?,
                    MODEL_YEAR = ?,
                    PRICE = ?,
                    CATEGORY_ID = ?,
                    TOTAL_SALES = ?
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getBrand());
            ps.setString(2, product.getModel());
            ps.setInt(3, product.getModelYear());
            ps.setDouble(4, product.getPrice());
            ps.setLong(5, product.getCategory().getId());
            ps.setDouble(6, product.getTotalSales());
            ps.executeUpdate();

            return true;
        } catch (SQLException sqlException) {
            throw new SQLException("An error ocurred while updating products in oracle sql");
        }
    }

    public Boolean delete(Product product) throws SQLException {
        String sql = """
                DELETE
                FROM PRODUCTS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, product.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException sqlException) {
            throw new SQLException("An error ocurred while removing products from oracle sql");
        }
    }

    private Product extractProductFromQuery(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Product product = new Product();
            product.setId(Long.parseLong(rs.getString("id")));
            product.setBrand(rs.getString("brand"));
            product.setModel(rs.getString("model"));
            product.setModelYear(rs.getInt("model_year"));
            product.setPrice(rs.getDouble("price"));

            Category category = new Category();
            category.setId(rs.getLong("category_id"));

            product.setCategory(category);
            product.setTotalSales(rs.getInt("total_sales"));

            return product;
        }

        throw new SQLException("An error ocurred while retrieving products from oracle sql");
    }
}