package br.com.ifsales.dao;

import br.com.ifsales.model.SalesPerson;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalesPersonDao {

    private final DataSource dataSource;

    public SalesPersonDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(SalesPerson salesPerson){
        Optional<SalesPerson> optional = getSalesPersonByEmail(salesPerson.getEmail());

        if(optional.isPresent())
            return false;

        String sql = """
                insert into salesPersons (name, email, phone, active)
                values (?, ?, ?, ?)""";

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

    public Optional<SalesPerson> getSalesPersonById(Long id)
    {
        String sql = """
                select *
                from salesPersons
                where salesPerson_id=?""";

        Optional<SalesPerson> optional = Optional.empty();


        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);

            optional = extractSalesPersonFromQuery(optional, ps);
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public Optional<SalesPerson> getSalesPersonByEmail(String email){
        String sql = """
                select *
                from salesPersons
                where email = ?""";

        Optional<SalesPerson> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);

            optional = extractSalesPersonFromQuery(optional, ps);
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public List<SalesPerson> getAllSalesPersons() {
        List<SalesPerson> salesPersons = new ArrayList<>();

        String sql = """
                select *
                from salesPersons""";

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

    public Boolean update(SalesPerson salesPerson) {
        String sql = """
                update salesPersons set
                    name = ?,
                    email = ?,
                    phone = ?,
                    active = ?
                where salesPerson_id = ?""";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, salesPerson.getName());
            ps.setString(2, salesPerson.getEmail());
            ps.setString(3, salesPerson.getPhone());
            ps.setBoolean(4, salesPerson.getActive());
            ps.setLong(5, salesPerson.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException("Erro ao atualizar dados", sqlException);
        }
    }

    public Boolean delete(SalesPerson salesPerson)
    {
        String sql = """
                delete from salesPersons
                where salesPerson_id = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, salesPerson.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao remover dados", sqlException);
        }
    }

    private Optional<SalesPerson> extractSalesPersonFromQuery(Optional<SalesPerson> optional, PreparedStatement ps) throws SQLException {
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
        return optional;
    }
}