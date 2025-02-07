package br.com.ifsales.servlets.helpers.region;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class DeleteRegionHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long regionId = Long.parseLong(req.getParameter("id"));
        RegionDao regionDao = new RegionDao(DataSourceSearcher.getInstance().getDataSource());

        return HelperUtils.safeDelete(req, regionDao.getRegionById(regionId), regionDao, "listRegions");
    }
}