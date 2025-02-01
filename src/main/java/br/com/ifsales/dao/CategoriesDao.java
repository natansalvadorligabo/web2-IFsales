package br.com.ifsales.dao;

import br.com.ifsales.model.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CategoriesDao {
    private final DataSource dataSource;

    public CategoriesDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Category category) {
        String sql = "call IFSALES_PKG.INSERT_CATEGORY(?,?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error during database save", e);
        }
        return true;
    }

    public Optional<Category> getCategoryById(Long id) {
        String sql = "SELECT * FROM CATEGORIES WHERE id = ?";
        Optional<Category> optional = Optional.empty();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getLong("id"));
                    category.setName(rs.getString("category_name"));
                    category.setDescription(rs.getString("description"));

                    optional = Optional.of(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during category database query", e);
        }

        return optional;
    }

    public Boolean update(Category category) {
        String sql = "update CATEGORIES set CATEGORY_NAME = ?, DESCRIPTION = ? where id = ?";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error during category database query", e);
        }
    }

    public Boolean delete(Long id) {
        String sql = "delete from CATEGORIES where id = ?";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error during category database query", e);
        }
    }
}