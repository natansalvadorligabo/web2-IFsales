package br.com.ifsales.servlets.helpers.region;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteRegionHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        Long regionId = Long.parseLong(req.getParameter("id"));

        RegionDao regionDao = new RegionDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Region> region = regionDao.getRegionById(regionId);


        if (region.isPresent()) {
            if (regionDao.delete(region.get())) return "redirect?action=listRegions";
        }

        return "redirect?action=home";
    }
}