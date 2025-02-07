package br.com.ifsales.dao;

import br.com.ifsales.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class UserDao implements Dao<User> {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(User user) throws SQLException {
        String sql = "call IFSALES_PKG.INSERT_USER(?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("An error ocurred while saving user to oracle sql");
        }

        return true;
    }

    public Optional<User> getUserByEmail(String email) throws SQLException {
        String sql = """
                SELECT *
                FROM USERS
                WHERE EMAIL = ?""";

        Optional<User> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(Long.parseLong(rs.getString("id")));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));

                    optional = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving user from oracle sql");
        }

        return optional;
    }

    @Override
    public Boolean update(User storable) throws SQLException {
        return null;
    }

    @Override
    public Boolean delete(Long id) throws SQLException {
        return null;
    }
}