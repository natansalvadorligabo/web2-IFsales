package br.com.ifsales.servlets.helpers.stores;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.model.Store;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class ListStoreHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());
        List<Store> stores = storeDao.getAll();


        req.setAttribute("stores", stores);

        return "/pages/home/stores/storeTable.jsp";
    }
}