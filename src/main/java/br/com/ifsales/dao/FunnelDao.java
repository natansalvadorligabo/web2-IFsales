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
            ps.setInt(7, funnel.getProductQuantity());

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
                FROM V_FUNNEL
                WHERE FUNNEL_ID = ?""";

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
  
    public List<Funnel> getAllFunnels() throws SQLException {
        List<Funnel> funnels = new LinkedList<>();

        String sql = """
                SELECT *
                FROM V_FUNNEL""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    funnels.add(createFunnelFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving funnel from oracle sql");
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
            ps.setInt(7, funnel.getProductQuantity());
            ps.setLong(8, funnel.getId());
            if (ps.executeUpdate() == 0) {
                throw new SQLDataException("Funnel not found");
            }

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
            if (ps.executeUpdate() == 0) {
                throw new SQLDataException("Funnel not found");
            }

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while removing funnel from oracle sql");
        }
    }

    private Funnel createFunnelFromResultSet(ResultSet rs) throws SQLException {
        Funnel funnel = new Funnel();
        funnel.setId(rs.getLong("FUNNEL_ID"));

        funnel.setCustomer(getCustomerFromResultSet(rs));
        funnel.setSalesperson(getSalesPersonFromResultSet(rs));
        funnel.setStore(getStoreFromResultSet(rs));
        funnel.setProduct(getProductFromResultSet(rs));

        funnel.setPaidDate(rs.getDate("FUNNEL_PAID_DATE").toLocalDate());
        funnel.setDiscount(rs.getDouble("FUNNEL_DISCOUNT"));
        funnel.setProductQuantity(rs.getInt("FUNNEL_PRODUCT_QT"));

        return funnel;
    }

    private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("CUSTOMER_ID"));
        customer.setCpf(rs.getString("CUSTOMER_CPF"));
        customer.setFirstName(rs.getString("CUSTOMER_FIRST_NAME"));
        customer.setLastName(rs.getString("CUSTOMER_LAST_NAME"));
        customer.setBirthDate(rs.getDate("CUSTOMER_BIRTH_DATE").toLocalDate());
        customer.setIncome(rs.getDouble("CUSTOMER_INCOME"));
        customer.setMobile(rs.getString("CUSTOMER_MOBILE"));
        customer.setProfessionalStatus(rs.getString("CUSTOMER_PROFESSIONAL_STATUS"));

        Region region = new Region();
        region.setId(rs.getLong("CUSTOMER_REGION_ID"));
        region.setName(rs.getString("CUSTOMER_REGION_NAME"));
        region.setCity(rs.getString("CUSTOMER_REGION_CITY"));
        region.setState(rs.getString("CUSTOMER_REGION_STATE"));

        customer.setRegion(region);

        return customer;
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

    private Store getStoreFromResultSet(ResultSet rs) throws SQLException {
        Store store = new Store();
        store.setId(rs.getLong("STORE_ID"));
        store.setName(rs.getString("STORE_NAME"));
        store.setCnpj(rs.getString("STORE_CNPJ"));
        store.setAddress(rs.getString("STORE_ADDRESS"));
        store.setPhone(rs.getString("STORE_PHONE"));

        Region region = new Region();
        region.setId(rs.getLong("STORE_REGION_ID"));
        region.setName(rs.getString("STORE_REGION_NAME"));
        region.setCity(rs.getString("STORE_REGION_CITY"));
        region.setState(rs.getString("STORE_REGION_STATE"));
        store.setRegion(region);

        return store;
    }

    private Product getProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("PRODUCT_ID"));
        product.setBrand(rs.getString("PRODUCT_BRAND"));
        product.setModel(rs.getString("PRODUCT_MODEL"));
        product.setModelYear(rs.getInt("PRODUCT_MODEL_YEAR"));
        product.setPrice(rs.getDouble("PRODUCT_PRICE"));

        Category category = new Category();
        category.setId(rs.getLong("PRODUCT_CATEGORY_ID"));
        category.setName(rs.getString("PRODUCT_CATEGORY_NAME"));
        category.setDescription(rs.getString("PRODUCT_CATEGORY_DESCRIPTION"));

        product.setTotalSales(rs.getInt("PRODUCT_TOTAL_SALES"));

        product.setCategory(category);

        return product;
    }
}