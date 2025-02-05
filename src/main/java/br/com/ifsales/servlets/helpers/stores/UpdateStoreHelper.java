package br.com.ifsales.servlets.helpers.stores;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateStoreHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        RegionDao regionDao = new RegionDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Region> region = regionDao.getRegionById(Long.parseLong(id));

        if (region.isPresent()) {
            req.setAttribute("region", region.get());
            return "/pages/home/regionForm.jsp";
        }

        return "redirect?action=listRegions";
    }
}