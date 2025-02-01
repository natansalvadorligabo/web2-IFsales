package br.com.ifsales.dao;

import br.com.ifsales.model.User;
import br.com.ifsales.utils.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(User user) throws SQLException {
        String sql = "call IFSALES_PKG.INSERT_USER(?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();
        }catch (SQLException e) {
            throw new SQLException("Error during user database save", e);
        }
        return true;
    }

    public Optional<User> getUserByEmail(String email) throws SQLException {
        String sql = "select * from users where email = ?";
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
            throw new SQLException("Error during user database query", e);
        }
        return optional;
    }
}