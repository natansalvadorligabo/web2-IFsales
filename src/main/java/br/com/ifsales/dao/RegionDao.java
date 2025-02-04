package br.com.ifsales.dao;

import br.com.ifsales.model.Region;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegionDao {

    private final DataSource dataSource;

    public RegionDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Region region) {
        String sql = "call IFSALES_PKG.INSERT_REGION(?, ?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, region.getName());
            ps.setString(2, region.getCity());
            ps.setString(3, region.getState());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error during region database save", e);
        }
        return true;
    }

    public Optional<Region> getRegionById(Long id) throws SQLException {
        Region region;

        String sql = """
                select *
                from regions
                where id=?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            region = extractRegionFromQuery(rs);
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during region database query", e);
        }

        return Optional.of(region);
    }

    public Optional<List<Region>> getRegionsByName(String name) throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
                select *
                from regions
                where region_name = ?""";

        Region region;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                regions.add(extractRegionFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during region database query", e);
        }
        return Optional.of(regions);
    }

    public Optional<List<Region>> getRegionsByCity(String city) throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
                select *
                from regions
                where city  = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, city);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                regions.add(extractRegionFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during region database query", e);
        }
        return Optional.of(regions);
    }

    public Optional<List<Region>> getRegionsByState(String state) throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
                select *
                from regions
                where state = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, state);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                regions.add(extractRegionFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during region database query", e);
        }
        return Optional.of(regions);
    }

    public Optional<List<Region>> getAllRegions() throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
                select *
                from regions""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
                regions.add(extractRegionFromQuery(rs));
        }
        catch (SQLException e)
        {
            throw new SQLException("Error during region database query", e);
        }
        return Optional.of(regions);
    }

    public Boolean update(Region region) throws SQLException {
        String sql = """
                update regions set
                    region_name = ?,
                    city = ?,
                    state = ?
                where id = ?""";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, region.getName());
            ps.setString(2, region.getCity());
            ps.setString(3, region.getState());
            ps.setLong(4, region.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException)
        {
            throw new SQLException("Error during region update", sqlException);
        }
    }

    public Boolean delete(Region region) throws SQLException {
        String sql = """
                delete from regions
                where id = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, region.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException sqlException) {
            throw new SQLException("Error during region remove", sqlException);
        }
    }

    private Region extractRegionFromQuery(ResultSet rs) throws SQLException {
        if (rs.next())
        {
            Region region = new Region();
            region.setId(Long.parseLong(rs.getString("id")));
            region.setName(rs.getString("name"));
            region.setCity(rs.getString("city"));
            region.setState(rs.getString("state"));

            return region;
        }

        throw new SQLException("Error during get region");
    }
}