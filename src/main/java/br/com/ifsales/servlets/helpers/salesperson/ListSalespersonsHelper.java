package br.com.ifsales.servlets.helpers.salesperson;

import br.com.ifsales.dao.SalespersonDao;
import br.com.ifsales.model.Salesperson;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class ListSalespersonsHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        SalespersonDao salespersonDao = new SalespersonDao(DataSourceSearcher.getInstance().getDataSource());
        List<Salesperson> salesperson = salespersonDao.getAllSalespersons();

        req.setAttribute("salespersons", salesperson);

        return "/pages/home/salesperson/salespersonTable.jsp";
    }
}