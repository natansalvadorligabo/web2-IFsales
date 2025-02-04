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

        if(optional.isPresent())
            return false;

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

    public List<Optional<Store>> getAll() throws SQLException {
        String sql = "SELECT * FROM STORES";
        List<Optional<Store>> stores = new LinkedList<>();

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
        String sql = "SELECT * FROM STORES WHERE id = ?";
        Optional<Store> optional = Optional.empty();

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
        update stores
        set STORE_NAME = ?,
            STORE_CNPJ = ?,
            REGION_ID = ?,
            ADDRESS = ?,
            PHONE = ?
        where id = ?""";

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
        String sql = "delete from stores where id = ?";

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

    private Store createStoreFromResultSet(ResultSet rs) throws SQLException {
        Store store = new Store();
        store.setId(rs.getLong("id"));
        store.setName(rs.getString("name"));
        store.setCnjp(rs.getString("cnjp"));
        store.setAddress(rs.getString("address"));
        store.setPhone(rs.getString("phone"));

        // Need to create a view to get the region complete
        Region region = new Region();
        region.setId(rs.getLong("region"));
        store.setRegion(region);

        return store;
    }
}