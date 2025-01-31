package br.com.ifsales.dao;

import br.com.ifsales.model.Store;
import br.com.ifsales.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StoreDao {

    private final DataSource dataSource;

    public StoreDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Boolean save(Store store) {
        Optional<Store> optional = getStoreById(store.getId());
        if(optional.isPresent()) {
            return false;
        }

        String sql = "call IFSALES_PKG.INSERT_STORE(?,?,?,?,?,?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, store.getName());
            ps.setString(2, store.getCnjp());
            ps.setLong(3, store.getRegion().getId());
            ps.setString(4, store.getAddress());
            ps.setString(5, store.getPhone());

            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return true;
    }

    public Optional<Store> getStoreById(Long id) {
        String sql = "SELECT * FROM STORES WHERE id = ?";
        Optional<Store> optional = Optional.empty();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Store store = new Store();
                    store.setId(rs.getLong("id"));
                    store.setName(rs.getString("name"));


                    optional = Optional.of(store);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }

        return optional;
    }

    public Boolean update(Store store) {
        String sql = "update stores set name = ?, cnjp = ?, region = ?, address = ?, phone = ? where id = ?";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, store.getName());
            ps.setString(2, store.getCnjp());
            ps.setLong(3, store.getRegion().getId());
            ps.setString(4, store.getAddress());
            ps.setString(5, store.getPhone());
            ps.setLong(6, store.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
    }

    public Boolean delete(Long id) {
        String sql = "delete from stores where id = ?";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
    }
}
