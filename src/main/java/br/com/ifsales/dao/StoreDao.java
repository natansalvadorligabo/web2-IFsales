package br.com.ifsales.dao;

import br.com.ifsales.model.Region;
import br.com.ifsales.model.Store;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class StoreDao {
    private final DataSource dataSource;

    public StoreDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Store store) throws SQLException {
        Optional<Store> optional = getStoreById(store.getId());

        if(optional.isPresent()) return false;

        String sql = "call IFSALES_PKG.INSERT_STORE(?,?,?,?,?)";
      
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, store.getName());
            ps.setString(2, store.getCnjp());
            ps.setLong(3, store.getRegion().getId());
            ps.setString(4, store.getAddress());
            ps.setString(5, store.getPhone());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while saving store to oracle sql");
        }

        return true;
    }

    public List<Optional<Store>> getAllStores() throws SQLException {
        List<Optional<Store>> stores = new LinkedList<>();

        String sql = """
            SELECT *
            FROM V_STORES""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    stores.add(Optional.of(createStoreFromResultSet(rs)));
            }
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving store from oracle sql");
        }

        return stores;
    }

    public Optional<Store> getStoreById(Long id) throws SQLException {
        Optional<Store> optional = Optional.empty();

        String sql = """
            SELECT *
            FROM V_STORES
            WHERE STORE_ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    optional = Optional.of(createStoreFromResultSet(rs));
            }
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while retrieving store from oracle sql");
        }

        return optional;
    }


    public Boolean update(Store store) throws SQLException {
        String sql = """
            UPDATE STORES
            SET STORE_NAME = ?,
                STORE_CNPJ = ?,
                REGION_ID = ?,
                ADDRESS = ?,
                PHONE = ?
            WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, store.getName());
            ps.setString(2, store.getCnjp());
            ps.setLong(3, store.getRegion().getId());
            ps.setString(4, store.getAddress());
            ps.setString(5, store.getPhone());
            ps.setLong(6, store.getId());
            ps.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while updating store in oracle sql");
        }
    }

    public Boolean delete(Long id) throws SQLException {
        String sql = """
        DELETE
        FROM STORES
        WHERE ID = ?""";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            throw new SQLException("An error ocurred while removing store from oracle sql");
        }
    }

    public static Store createStoreFromResultSet(ResultSet rs) throws SQLException {
        Store store = new Store();
        store.setId(rs.getLong("STORE_ID"));
        store.setName(rs.getString("STORE_NAME"));
        store.setCnjp(rs.getString("STORE_CNPJ"));
        store.setAddress(rs.getString("STORE_ADDRESS"));
        store.setPhone(rs.getString("STORE_PHONE"));

        Region region = new Region();
        region.setId(rs.getLong("REGION_ID"));
        region.setName(rs.getString("REGION_NAME"));
        region.setCity(rs.getString("REGION_CITY"));
        region.setState(rs.getString("REGION_STATE"));
        store.setRegion(region);

        return store;
    }
}