package br.com.ifsales.dao;

import br.com.ifsales.model.Salesperson;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalespersonDao implements Dao<Salesperson>{
    private final DataSource dataSource;

    public SalespersonDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Salesperson salesperson) throws SQLException {
        String sql = "call IFSALES_PKG.INSERT_SALESPERSON(?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, salesperson.getName());
            ps.setString(2, salesperson.getEmail());
            ps.setString(3, salesperson.getPhone());
            ps.setInt(4, salesperson.getActive() ? 1 : 0);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while saving salesperson to oracle sql");
        }

        return true;
    }

    public Optional<Salesperson> getSalespersonById(Long id) throws SQLException {
        Optional<Salesperson> optional = Optional.empty();

        String sql = """
                SELECT *
                FROM SALESPERSONS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            optional = extractSalespersonFromQuery(optional, ps);
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving salespersons from oracle sql");
        }

        return optional;
    }

    public Optional<Salesperson> getSalespersonByEmail(String email) throws SQLException {
        Optional<Salesperson> optional = Optional.empty();

        String sql = """
                SELECT *
                FROM SALESPERSONS
                WHERE EMAIL = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);

            optional = extractSalespersonFromQuery(optional, ps);
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving salespersons from oracle sql");
        }

        return optional;
    }

    public List<Salesperson> getAllSalespersons() throws SQLException {
        List<Salesperson> salespersons = new ArrayList<>();

        String sql = """
                SELECT *
                FROM SALESPERSONS""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Salesperson salesperson = new Salesperson();
                salesperson.setId(rs.getLong("id"));
                salesperson.setName(rs.getString("name"));
                salesperson.setEmail(rs.getString("email"));
                salesperson.setPhone(rs.getString("phone"));
                salesperson.setActive(rs.getBoolean("active"));

                salespersons.add(salesperson);
            }

            return salespersons;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving salespersons from oracle sql");
        }
    }

    public Boolean update(Salesperson salesperson) throws SQLException {
        String sql = """
                UPDATE SALESPERSONS
                SET NAME = ?,
                    EMAIL = ?,
                    PHONE = ?,
                    ACTIVE = ?
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, salesperson.getName());
            ps.setString(2, salesperson.getEmail());
            ps.setString(3, salesperson.getPhone());
            ps.setBoolean(4, salesperson.getActive());
            ps.setLong(5, salesperson.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException sqlException) {
            throw new SQLException("An error ocurred while updating salespersons in oracle sql");
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
                DELETE
                FROM SALESPERSONS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException sqlException) {
            throw new SQLException("An error ocurred while removing salespersons from oracle sql");
        }
    }

    private Optional<Salesperson> extractSalespersonFromQuery(Optional<Salesperson> optional, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Salesperson salesperson = new Salesperson();
                salesperson.setId(Long.parseLong(rs.getString("id")));
                salesperson.setName(rs.getString("name"));
                salesperson.setEmail(rs.getString("email"));
                salesperson.setPhone(rs.getString("phone"));
                salesperson.setActive(rs.getBoolean("active"));

                optional = Optional.of(salesperson);
            }

            return optional;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving salespersons from oracle sql");
        }
    }
}