package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.RegionDao;
import br.com.ifsales.model.Region;
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

        if (id == 0) {
            Optional<Region> registered = regionDao.getRegionByName(name);

            if (registered.isPresent()) {
                req.setAttribute("result", "already exists");
                return "/pages/home/regionForm.jsp";
            } else {
                if (regionDao.save(region))
                    req.setAttribute("result", "registered successfully");
                else
                    req.setAttribute("result", "not registered");
            }
        } else {
            region.setId(id);

            if (regionDao.update(region))
                req.setAttribute("result", "saved");
        }

        return "redirect?action=listRegions";
    }
}