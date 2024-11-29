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

    public Boolean save(User user){
        Optional<User> optional = getUserByEmail(user.getEmail());
        if(optional.isPresent()) {
            return false;
        }
        String sql = "insert into users (email, password) values (?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return true;
    }

    public Optional<User> getUserByEmail(String email){
        String sql = "select * from users where email = ?";
        Optional<User> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(Long.parseLong(rs.getString("user_id")));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));

                    optional = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password){
        String passwordEncrypted = PasswordEncoder.encode(password);

        String sql = "select * from users where email = ? and password = ?";
        Optional<User> optional = Optional.empty();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, passwordEncrypted);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));

                    optional = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }
}