package br.com.ifsales.dao;

import br.com.ifsales.model.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CategoryDao implements Dao<Category>{
    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Category category) throws SQLException {
        String sql = "CALL IFSALES_PKG.INSERT_CATEGORY(?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());

            ps.executeUpdate();
        } catch (SQLException e) {
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
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    optional = Optional.of(createCategoryFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving category from oracle sql");
        }

        return optional;
    }

    public Optional<Category> getCategoryByName(String name) throws SQLException {
        Optional<Category> optional = Optional.empty();

        String sql = """
                SELECT *
                FROM CATEGORIES
                WHERE CATEGORY_NAME = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    optional = Optional.of(createCategoryFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving category from oracle sql");
        }

        return optional;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new LinkedList<>();

        String sql = """
                SELECT *
                FROM CATEGORIES""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    categories.add(createCategoryFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving store from oracle sql");
        }

        return categories;
    }

    public Boolean update(Category category) throws SQLException {
        String sql = """
                UPDATE CATEGORIES
                SET CATEGORY_NAME = ?,
                    DESCRIPTION = ?
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getId());

            if (ps.executeUpdate() == 0) {
                throw new SQLDataException("Category not found");
            }

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while updating category from oracle sql");
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
                DELETE
                FROM CATEGORIES
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLDataException("Category not found");
            }

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while removing category from oracle sql");
        }
    }

    private Category createCategoryFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("ID"));
        category.setName(rs.getString("CATEGORY_NAME"));
        category.setDescription(rs.getString("DESCRIPTION"));

        return category;
    }
}