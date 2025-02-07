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

public class ProductDao implements Dao<Product>{
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
        String sql = """
                SELECT *
                FROM PRODUCTS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(extractProductFromQuery(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
        }
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
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
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
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
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
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
        }

        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByPrice(Double price) throws SQLException {
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
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
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
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
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
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
        }

        return Optional.of(products);
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM V_PRODUCTS""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                products.add(extractProductFromResultSet(rs));
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving products in oracle sql", e);
        }

        return products;
    }

    public static Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("PRODUCT_ID"));
        product.setBrand(rs.getString("PRODUCT_BRAND"));
        product.setModel(rs.getString("PRODUCT_MODEL"));
        product.setModelYear(rs.getInt("PRODUCT_MODEL_YEAR"));
        product.setPrice(rs.getDouble("PRODUCT_PRICE"));

        Category category = new Category();
        category.setId(rs.getLong("CATEGORY_ID"));
        category.setName(rs.getString("CATEGORY_NAME"));
        category.setDescription(rs.getString("CATEGORY_DESCRIPTION"));

        product.setTotalSales(rs.getInt("PRODUCT_TOTAL_SALES"));

        product.setCategory(category);

        return product;
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
            ps.setLong(7, product.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while updating products in oracle sql", e);
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
                DELETE
                FROM PRODUCTS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while removing products from oracle sql");
        }
    }

    private Product extractProductFromQuery(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
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
}