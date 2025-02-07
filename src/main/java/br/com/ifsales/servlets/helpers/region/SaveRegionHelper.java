package br.com.ifsales.servlets.helpers.region;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.model.Region;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Optional;

public class SaveRegionHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        String state = req.getParameter("state");

        Region region = new Region();
        region.setName(name);
        region.setCity(city);
        region.setState(state);

        RegionDao regionDao = new RegionDao(DataSourceSearcher.getInstance().getDataSource());


        String result = HelperUtils.saveOrUpdate(req, region, regionDao, id);
        if (result.equals("registerError")) return "/pages/home/region/regionForm.jsp";

        return "redirect?action=listRegions";
    }
}