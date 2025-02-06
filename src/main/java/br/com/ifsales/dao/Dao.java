package br.com.ifsales.dao;

import java.sql.SQLException;

public interface Dao<T extends Storable> {
    Boolean save(T storable) throws SQLException;

    Boolean update(T storable) throws SQLException;
}
