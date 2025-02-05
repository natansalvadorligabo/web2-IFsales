package br.com.ifsales.servlets.helpers.store;

import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteStoreHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long storeId = Long.parseLong(req.getParameter("id"));
        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());

        if (storeDao.delete(storeId))
            return "redirect?action=listStores";

        return "redirect?action=home";
    }
}