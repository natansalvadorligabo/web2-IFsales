package br.com.ifsales.dao;

import br.com.ifsales.model.Salesperson;
import br.com.ifsales.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalespersonDao {

    private final DataSource dataSource;

    public SalespersonDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Salesperson salesperson) {
        String sql = "call IFSALES_PKG.INSERT_SALESPERSON(?, ?, ?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, salesperson.getName());
            ps.setString(2, salesperson.getEmail());
            ps.setString(3, salesperson.getPhone());
            ps.setBoolean(4, salesperson.getActive());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error during salesperson database save", e);
        }
        return true;
    }

    public Optional<Salesperson> getSalespersonById(Long id) {
        String sql = """
                select *
                from salespersons
                where id=?""";

        Optional<Salesperson> optional = Optional.empty();


        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);

            optional = extractSalespersonFromQuery(optional, ps);
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public Optional<Salesperson> getSalespersonByEmail(String email) {
        String sql = """
                select *
                from salespersons
                where email = ?""";

        Optional<Salesperson> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);

            optional = extractSalespersonFromQuery(optional, ps);
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public List<Salesperson> getAllSalespersons() {
        List<Salesperson> salesperson = new ArrayList<>();

        String sql = """
                select *
                from salespersons""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Salesperson salespersonFinded = new Salesperson();
                salespersonFinded.setId(rs.getLong("salesperson_id"));
                salespersonFinded.setName(rs.getString("name"));
                salespersonFinded.setEmail(rs.getString("email"));
                salespersonFinded.setPhone(rs.getString("phone"));
                salespersonFinded.setActive(rs.getBoolean("active"));

                salesperson.add(salespersonFinded);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return salesperson;
    }

    public Boolean update(Salesperson salesperson) {
        String sql = """
                update salespersons set
                    name = ?,
                    email = ?,
                    phone = ?,
                    active = ?
                where id = ?""";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, salesperson.getName());
            ps.setString(2, salesperson.getEmail());
            ps.setString(3, salesperson.getPhone());
            ps.setBoolean(4, salesperson.getActive());
            ps.setLong(5, salesperson.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException("Erro ao atualizar dados", sqlException);
        }
    }

    public Boolean delete(Salesperson salesperson) {
        String sql = """
                delete from salespersons
                where id = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, salesperson.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao remover dados", sqlException);
        }
    }

    private Optional<Salesperson> extractSalespersonFromQuery(Optional<Salesperson> optional, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                Salesperson salesperson = new Salesperson();
                salesperson.setId(Long.parseLong(rs.getString("salesPerson_id")));
                salesperson.setName(rs.getString("name"));
                salesperson.setEmail(rs.getString("email"));
                salesperson.setPhone(rs.getString("phone"));
                salesperson.setActive(rs.getBoolean("active"));

                optional = Optional.of(salesperson);
            }
        }
        return optional;
    }
}