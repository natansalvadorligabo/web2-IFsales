package br.com.ifsales.servlets.helpers.store;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.model.Store;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListStoreHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());
        List<Store> stores;

        try {
            stores = storeDao.getAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            stores = new ArrayList<>();
        }

        req.setAttribute("stores", stores);

        return "/pages/home/stores/storeTable.jsp";
    }
}