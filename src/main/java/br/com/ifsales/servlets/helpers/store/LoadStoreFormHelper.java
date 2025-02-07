package br.com.ifsales.servlets.helpers.store;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class LoadStoreFormHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        RegionDao regionDao = new RegionDao(DataSourceSearcher.getInstance().getDataSource());
        List<Region> regions = regionDao.getAllRegions();
        req.setAttribute("regions", regions);

        return "/pages/home/store/storeForm.jsp";
    }
}