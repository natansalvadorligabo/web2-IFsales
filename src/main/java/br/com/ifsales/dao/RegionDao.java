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

    public Boolean save(Region region) {
        String sql = "call IFSALES_PKG.INSERT_REGION(?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, region.getName());
            ps.setString(2, region.getCity());
            ps.setString(3, region.getState());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error during region database save", e);
        }
        return true;
    }

    public Optional<Region> getRegionById(Long id) {
        String sql = "select * from regions where id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractRegionFromQuery(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during region database query", e);
        }
        return Optional.empty();
    }

    public Optional<Region> getRegionByName(String name) {
        String sql = "select * from regions where region_name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractRegionFromQuery(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during region database query", e);
        }
        return Optional.empty();
    }

    public List<Region> getRegionsByCity(String city) {
        List<Region> regions = new ArrayList<>();
        String sql = "select * from regions where city = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    regions.add(extractRegionFromQuery(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during region database query", e);
        }
        return regions;
    }

    public List<Region> getRegionsByState(String state) {
        List<Region> regions = new ArrayList<>();
        String sql = "select * from regions where state = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, state);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    regions.add(extractRegionFromQuery(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during region database query", e);
        }
        return regions;
    }

    public List<Region> getAllRegions() {
        List<Region> regions = new ArrayList<>();
        String sql = "select * from regions";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                regions.add(extractRegionFromQuery(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during region database query", e);
        }
        return regions;
    }

    public Boolean update(Region region) {
        String sql = "update regions set region_name = ?, city = ?, state = ? where id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, region.getName());
            ps.setString(2, region.getCity());
            ps.setString(3, region.getState());
            ps.setLong(4, region.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error during region update", e);
        }
    }

    public Boolean delete(Region region) {
        String sql = "delete from regions where id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, region.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error during region remove", e);
        }
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