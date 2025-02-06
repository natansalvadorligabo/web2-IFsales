package br.com.ifsales.dao;

import br.com.ifsales.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FunnelDao {
    private final DataSource dataSource;

    public FunnelDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Funnel funnel) throws SQLException {
        String sql = "CALL IFSALES_PKG.INSERT_FUNNEL(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, funnel.getCustomer().getId());
            ps.setLong(2, funnel.getSalesperson().getId());
            ps.setLong(3, funnel.getStore().getId());
            ps.setLong(4, funnel.getProduct().getId());
            ps.setDate(5, Date.valueOf((funnel.getPaidDate())));
            ps.setDouble(6, funnel.getDiscount());
            ps.setInt(7, funnel.getProductQuanity());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while saving funnel to oracle sql");
        }

        return true;
    }

    public Optional<Funnel> getFunnelById(Long id) throws SQLException {
        Optional<Funnel> optional = Optional.empty();

        String sql = """
                SELECT *
                FROM FUNNEL
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of((createFunnelFromResultSet(rs)));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving funnel from oracle sql");
        }

        return optional;
    }

    public List<Optional<Funnel>> getAllFunnels() throws SQLException {
        List<Optional<Funnel>> funnels = new LinkedList<>();

        String sql = """
                SELECT *
                FROM V_FUNNEL""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    funnels.add(Optional.of(createFunnelFromResultSet(rs)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving store from oracle sql");
        }

        return funnels;
    }

    public Boolean update(Funnel funnel) throws SQLException {
        String sql = """
                UPDATE FUNNEL
                SET CUSTOMER_ID = ?,
                    SALESPERSON_ID = ?,
                    STORE_ID = ?,
                    PRODUCT_ID = ?,
                    PAID_DATE = ?,
                    DISCOUNT = ?,
                    PRODUCT_QUANTITY = ?
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, funnel.getCustomer().getId());
            ps.setLong(2, funnel.getSalesperson().getId());
            ps.setLong(3, funnel.getStore().getId());
            ps.setLong(4, funnel.getProduct().getId());
            ps.setDate(5, Date.valueOf(funnel.getPaidDate()));
            ps.setDouble(6, funnel.getDiscount());
            ps.setInt(7, funnel.getProductQuanity());
            ps.setLong(8, funnel.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while updating funnel from oracle sql");
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
                DELETE
                FROM FUNNEL
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while removing funnel from oracle sql");
        }
    }

    private Funnel createFunnelFromResultSet(ResultSet rs) throws SQLException {
        Funnel funnel = new Funnel();
        funnel.setId(rs.getLong("ID"));

        funnel.setCustomer(CustomerDao.extractCustomerFromResultSet(rs));
        funnel.setSalesperson(getSalesPersonFromResultSet(rs));
        funnel.setStore(StoreDao.createStoreFromResultSet(rs));
        funnel.setProduct(ProductDao.extractProductFromResultSet(rs));

        funnel.setPaidDate(rs.getDate("FUNNEL_PAID_DATE").toLocalDate());
        funnel.setDiscount(rs.getDouble("FUNNEL_DISCOUNT"));
        funnel.setProductQuanity(rs.getInt("FUNNEL_PRODUCT_QT"));

        return funnel;
    }

    private Salesperson getSalesPersonFromResultSet(ResultSet rs) throws SQLException {
        Salesperson salesperson = new Salesperson();
        salesperson.setId(rs.getLong("SALESPERSON_ID"));
        salesperson.setName(rs.getString("SALESPERSON_NAME"));
        salesperson.setEmail(rs.getString("SALESPERSON_EMAIL"));
        salesperson.setPhone(rs.getString("SALESPERSON_PHONE"));
        salesperson.setActive(rs.getBoolean("SALESPERSON_ACTIVE"));

        return salesperson;
    }
}