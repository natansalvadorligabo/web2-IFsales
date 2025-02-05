package br.com.ifsales.servlets.helpers.store;

import java.util.Optional;

import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.model.Store;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStoreHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Store> store = storeDao.getById(Long.parseLong(id));

        if (store.isPresent()) {
            req.setAttribute("store", store.get());
            return "/pages/home/storeForm.jsp";
        }

        return "redirect?action=listStores";
    }
}