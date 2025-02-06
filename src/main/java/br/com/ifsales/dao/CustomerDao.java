package br.com.ifsales.dao;

import br.com.ifsales.model.Customer;
import br.com.ifsales.model.Region;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class    CustomerDao {
    private final DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Customer customer) throws SQLException {
        String sql = "CALL IFSALES_PKG.INSERT_CUSTOMER(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            createQuery(customer, ps);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while saving customer to oracle sql");
        }

        return true;
    }

    public Optional<Customer> getCustomerById(Long id) throws SQLException {
        Optional<Customer> optional = Optional.empty();

        String sql = """
                SELECT *
                FROM CUSTOMERS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of((createCustomerFromResultSet(rs)));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving customer from oracle sql");
        }

        return optional;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new LinkedList<>();

        String sql = """
                SELECT *
                FROM V_CUSTOMERS""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    customers.add(extractCustomerFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving customer from oracle sql");
        }

        return customers;
    }

    public Boolean update(Customer customer) throws SQLException {
        String sql = """
                UPDATE CUSTOMERS
                SET CPF = ?,
                    REGION_ID = ?,
                    FIRST_NAME = ?,
                    LAST_NAME = ?,
                    BIRTH_DATE = ?,
                    INCOME = ?,
                    MOBILE = ?,
                    PROFESSIONAL_STATUS = ?
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            createQuery(customer, ps);
            ps.setLong(9, customer.getId());

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while updating customer from oracle sql");
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
                DELETE
                FROM CUSTOMERS
                WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new SQLException("An error ocurred while removing customer from oracle sql");
        }
    }

    public static Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
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
        region.setId(rs.getLong("REGION_ID"));
        region.setName(rs.getString("REGION_NAME"));
        region.setCity(rs.getString("REGION_CITY"));
        region.setState(rs.getString("REGION_STATE"));

        customer.setRegion(region);

        return customer;
    }

    private void createQuery(Customer customer, PreparedStatement ps) throws SQLException {
        ps.setString(1, customer.getCpf());
        ps.setLong(2, customer.getRegion().getId());
        ps.setString(3, customer.getFirstName());
        ps.setString(4, customer.getLastName());
        ps.setDate(5, Date.valueOf((customer.getBirthDate())));
        ps.setDouble(6, customer.getIncome());
        ps.setString(7, customer.getMobile());
        ps.setString(8, customer.getProfessionalStatus());
    }

    private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("ID"));
        customer.setCpf(rs.getString("CPF"));

        Region region = new Region();
        region.setId(rs.getLong("REGION_ID"));

        customer.setRegion(region);
        customer.setFirstName(rs.getString("FIRST_NAME"));
        customer.setLastName(rs.getString("LAST_NAME"));
        customer.setBirthDate(rs.getDate("BIRTH_DATE").toLocalDate());
        customer.setIncome(rs.getDouble("INCOME"));
        customer.setMobile(rs.getString("MOBILE"));
        customer.setProfessionalStatus(rs.getString("PROFESSIONAL_STATUS"));
        customer.setRegion(region);

        return customer;
    }
}