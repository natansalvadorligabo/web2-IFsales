package br.com.ifsales.dao;

import br.com.ifsales.model.SalesPerson;
import br.com.ifsales.utils.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalesPersonDao {

    private DataSource dataSource;

    public SalesPersonDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(SalesPerson salesPerson){
        Optional<SalesPerson> optional = getSalesPersonByEmail(salesPerson.getEmail());

        if(optional.isPresent())
            return false;

        String sql = "insert into salesPersons (name, email, phone, active) values (?, ?, ?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, salesPerson.getName());
            ps.setString(2, salesPerson.getEmail());
            ps.setString(3, salesPerson.getPhone());
            ps.setBoolean(4, salesPerson.getActive());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return true;
    }

    public Optional<SalesPerson> getSalesPersonByEmail(String email){
        String sql = "select * from salesPersons where email = ?";
        Optional<SalesPerson> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    SalesPerson salesPerson = new SalesPerson();
                    salesPerson.setId(Long.parseLong(rs.getString("salesPerson_id")));
                    salesPerson.setName(rs.getString("name"));
                    salesPerson.setEmail(rs.getString("email"));
                    salesPerson.setPhone(rs.getString("phone"));
                    salesPerson.setActive(rs.getBoolean("active"));

                    optional = Optional.of(salesPerson);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public List<SalesPerson> getAllSalesPersons() {
        List<SalesPerson> salesPersons = new ArrayList<>();

        String sql = "select * from salesPersons";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                SalesPerson salesPerson = new SalesPerson();
                salesPerson.setId(rs.getLong("salesPerson_id"));
                salesPerson.setName(rs.getString("name"));
                salesPerson.setEmail(rs.getString("email"));
                salesPerson.setPhone(rs.getString("phone"));
                salesPerson.setActive(rs.getBoolean("active"));

                salesPersons.add(salesPerson);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return salesPersons;
    }
}