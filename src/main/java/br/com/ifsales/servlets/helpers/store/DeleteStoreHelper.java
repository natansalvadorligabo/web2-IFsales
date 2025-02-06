package br.com.ifsales.servlets.helpers.store;

import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.model.Store;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class DeleteStoreHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long storeId = Long.parseLong(req.getParameter("id"));
        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());

        Optional<Store> store = storeDao.getById(storeId);

        try {
            if (store.isPresent() && storeDao.delete(storeId)) {
                req.setAttribute("result", "deleteSuccess");
                return "redirect?action=listStores";
            }
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }

        req.setAttribute("result", "deleteError");
        return "redirect?action=listStores";
    }
}