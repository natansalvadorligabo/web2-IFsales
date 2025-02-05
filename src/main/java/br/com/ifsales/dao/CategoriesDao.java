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

    public Boolean save(Category category) throws SQLException {
        String sql = "CALL IFSALES_PKG.INSERT_CATEGORY(?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while saving category to oracle sql");
        }
        return true;
    }

    public Optional<Category> getCategoryById(Long id) throws SQLException {
        Optional<Category> optional = Optional.empty();

        String sql = """
            SELECT *
            FROM CATEGORIES
            WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
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
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving category from oracle sql");
        }

        return optional;
    }

    public Boolean update(Category category) throws SQLException {
        String sql = """
            UPDATE CATEGORIES
            SET CATEGORY_NAME = ?,
                DESCRIPTION = ?
            WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while updating category from oracle sql");
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
            DELETE
            FROM CATEGORIES
            WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while removing category from oracle sql");
        }
    }
}