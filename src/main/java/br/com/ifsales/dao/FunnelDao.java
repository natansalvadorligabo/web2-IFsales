package br.com.ifsales.dao;

import br.com.ifsales.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class FunnelDao implements Dao<Funnel>{
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

    public Optional<Integer> getTotalProductsSold() throws SQLException {
        Optional<Integer> optional = Optional.empty();

        String sql = """
                SELECT IFSALES_PKG.REC_TOTAL_PRODUCTS_SOLD() as total_products_sold
                FROM DUAL
        """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(rs.getInt("total_products_sold"));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving funnel from oracle sql");
        }

        return optional;
    }

    public Optional<Double> getAverageTicket() throws SQLException {
        Optional<Double> optional = Optional.empty();

        String sql = """
                SELECT IFSALES_PKG.REC_AVERAGE_TICKET() as average_ticket
                FROM DUAL
        """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(rs.getDouble("average_ticket"));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving funnel from oracle sql");
        }

        return optional;
    }

    public Optional<Double> getTotalSales() throws SQLException {
        Optional<Double> optional = Optional.empty();

        String sql = """
                SELECT IFSALES_PKG.REC_TOTAL_SALES() as total_sales
                FROM DUAL
        """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(rs.getDouble("total_sales"));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving funnel from oracle sql");
        }

        return optional;
    }

    public List<Map<String, Object>> getSalesByRegion() throws SQLException {
        List<Map<String, Object>> results = new LinkedList<>();

        String sql = """
        SELECT CUSTOMER_REGION_ID   AS region_id
             , CUSTOMER_REGION_NAME AS region_name
             , IFSALES_PKG.REC_TOTAL_SALES(null, null, CUSTOMER_REGION_ID) AS total_sales
        FROM v_funnel
        GROUP BY CUSTOMER_REGION_ID, CUSTOMER_REGION_NAME, IFSALES_PKG.REC_TOTAL_SALES(null, null, CUSTOMER_REGION_ID)
        ORDER BY region_id
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("region_name", rs.getString("region_name"));
                row.put("total_sales", rs.getInt("total_sales"));
                results.add(row);
            }
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving sales data by month", e);
        }

        return results;
    }

    public List<Map<String, Object>> getSalesByMonth() throws SQLException {
        List<Map<String, Object>> results = new LinkedList<>();

        String sql = """
        SELECT to_char(FUNNEL_PAID_DATE, 'MM') AS month
             , IFSALES_PKG.REC_TOTAL_SALES(null, extract(month from(FUNNEL_PAID_DATE)), null) as total_sales
        FROM v_funnel
        GROUP BY to_char(FUNNEL_PAID_DATE, 'MM'), IFSALES_PKG.REC_TOTAL_SALES(null, extract(month from(FUNNEL_PAID_DATE)), null)
        ORDER BY month
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("month", rs.getString("month"));
                row.put("total_sales", rs.getInt("total_sales"));
                results.add(row);
            }
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving sales data by month", e);
        }

        return results;
    }

    public List<Map<String, Object>> getSalesByCategory() throws SQLException {
        List<Map<String, Object>> results = new LinkedList<>();

        String sql = """
            SELECT PRODUCT_CATEGORY_ID, PRODUCT_CATEGORY_NAME as category_name, IFSALES_PKG.REC_TOTAL_SALES(PRODUCT_CATEGORY_ID, null, null) AS total_sales
            FROM V_FUNNEL
            GROUP BY PRODUCT_CATEGORY_ID,PRODUCT_CATEGORY_NAME
            ORDER BY total_sales DESC""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("category_name", rs.getString("category_name"));
                row.put("total_sales", rs.getDouble("total_sales"));
                results.add(row);
            }
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving data from Oracle SQL", e);
        }

        return results;
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