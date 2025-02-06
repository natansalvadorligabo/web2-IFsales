package br.com.ifsales.servlets.helpers.store;

import java.sql.SQLException;
import java.util.Optional;

import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.model.Store;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SaveStoreHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        Store store = new Store();
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String cnpj = req.getParameter("cnpj");
        String phone = req.getParameter("phone");
        String regionId = req.getParameter("regionId");

        Region region = new Region();
        region.setId(Long.parseLong(regionId));

        store.setName(name);
        store.setAddress(address);
        store.setCnpj(cnpj);
        store.setPhone(phone);
        store.setRegion(region);

        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());

        if (id == 0) {
            Optional<Store> registered = storeDao.getByCnpj(cnpj);

            if (registered.isPresent()) {
                req.setAttribute("result", "already exists");
                return "/pages/home/store/storeForm.jsp";
            } else {
                if (storeDao.save(store))
                    req.setAttribute("result", "registered successfully");
                else
                    req.setAttribute("result", "not registered");
            }
        } else {
            store.setId(id);

            if (storeDao.update(store))
                req.setAttribute("result", "saved");
        }

        return "redirect?action=listStores";
    }
}