package br.com.ifsales.dao;

import br.com.ifsales.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {

    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Product product) {
        String sql = "call IFSALES_PKG.INSERT_PRODUCT(?, ?, ?, ?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, product.getBrand());
            ps.setString(2, product.getModel());
            ps.setInt(3, product.getModelYear());
            ps.setDouble(4, product.getPrice());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error during product database save", e);
        }
        return true;
    }

    public Optional<Product> getProductById(Long id) throws SQLException {
        Product product;

        String sql = """
                select *
                from products
                where id=?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            product = extractProductFromQuery(rs);
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }

        return Optional.of(product);
    }

    public Optional<List<Product>> getProductsByBrand(String brand) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products
                where brand = ?""";

        Product product;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, brand);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByModel(String model) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products
                where model = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, model);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByModelYear(int modelYear) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products
                where MODEL_YEAR = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, modelYear);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByPrice(Double price) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products
                where price = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setDouble(1, price);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByCategoryId(Long categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products
                where category_id = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Optional<List<Product>> getProductsByTotalSales(int totalSales) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products
                where total_sales = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, totalSales);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Optional<List<Product>> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = """
                select *
                from products""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
                products.add(extractProductFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during product database query", e);
        }
        return Optional.of(products);
    }

    public Boolean update(Product product) throws SQLException {
        String sql = """
                update products set
                    brand = ?,
                    model = ?,
                    model_year = ?,
                    price = ?,
                    category_id = ?,
                    total_sales = ?
                where id = ?""";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, product.getBrand());
            ps.setString(2, product.getModel());
            ps.setInt(3, product.getModelYear());
            ps.setDouble(4, product.getPrice());
            ps.setLong(5, product.getCategoryId());
            ps.setDouble(6, product.getTotalSales());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException)
        {
            throw new SQLException("Error during product update", sqlException);
        }
    }

    public Boolean delete(Product product) throws SQLException {
        String sql = """
                delete from products
                where id = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, product.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException) {
            throw new SQLException("Error during product remove", sqlException);
        }
    }

    private Product extractProductFromQuery(ResultSet rs) throws SQLException {
        if (rs.next())
        {
            Product product = new Product();
            product.setId(Long.parseLong(rs.getString("id")));
            product.setBrand(rs.getString("brand"));
            product.setModel(rs.getString("model"));
            product.setModelYear(rs.getInt("model_year"));
            product.setPrice(rs.getDouble("price"));
            product.setCategoryId(rs.getLong("category_id"));
            product.setTotalSales(rs.getInt("total_sales"));

            return product;
        }

        throw new SQLException("Error during get product");
    }
}