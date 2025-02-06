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
        this.dataSource = dataSource;
    }

    public Boolean save(Region region) throws SQLException {
        String sql = "call IFSALES_PKG.INSERT_REGION(?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, region.getName());
            ps.setString(2, region.getCity());
            ps.setString(3, region.getState());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while saving region to oracle sql");
        }

        return true;
    }

    public Optional<Region> getRegionById(Long id) throws SQLException {
        String sql = """
            SELECT *
            FROM REGIONS
            WHERE ID = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(extractRegionFromQuery(rs));
            }
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving store from oracle sql");
        }

        return Optional.empty();
    }

    public Optional<Region> getRegionByName(String name) throws SQLException {
        String sql = """
            SELECT *
            FROM REGIONS
            WHERE REGION_NAME = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(extractRegionFromQuery(rs));
            }
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving region from oracle sql");
        }

        return Optional.empty();
    }

    public List<Region> getRegionsByCity(String city) throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM REGIONS
            WHERE CITY = ?""";

        return getRegions(city, regions, sql);
    }

    public List<Region> getRegionsByState(String state) throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM REGIONS
            WHERE STATE = ?""";

        return getRegions(state, regions, sql);
    }

    public List<Region> getAllRegions() throws SQLException {
        List<Region> regions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM REGIONS""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
                regions.add(extractRegionFromQuery(rs));
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving region from oracle sql");
        }

        return regions;
    }

    public Boolean update(Region region) throws SQLException {
        String sql = """
            UPDATE REGIONS
            SET REGION_NAME = ?,
                CITY = ?,
                STATE = ?
            WHERE ID = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, region.getName());
            ps.setString(2, region.getCity());
            ps.setString(3, region.getState());
            ps.setLong(4, region.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while updating region in oracle sql");
        }
    }

    public Boolean delete(Region region) throws SQLException {
        String sql = """
            DELETE
            FROM REGIONS
            WHERE ID = ?""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, region.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while removing region from oracle sql");
        }
    }

    private List<Region> getRegions(String city, List<Region> regions, String sql) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    regions.add(extractRegionFromQuery(rs));
            }
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving region from oracle sql");
        }

        return regions;
    }

    private Region extractRegionFromQuery(ResultSet rs) throws SQLException {
        Region region = new Region();

        region.setId(rs.getLong("id"));
        region.setName(rs.getString("region_name"));
        region.setCity(rs.getString("city"));
        region.setState(rs.getString("state"));

        return region;
    }
}