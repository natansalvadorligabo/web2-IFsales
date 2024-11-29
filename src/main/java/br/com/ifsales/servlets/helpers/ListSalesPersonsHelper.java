package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.SalesPersonDao;
import br.com.ifsales.model.SalesPerson;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ListSalesPersonsHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        SalesPersonDao salesPersonDao = new SalesPersonDao(DataSourceSearcher.getInstance().getDataSource());
        List<SalesPerson> salesPersons = salesPersonDao.getAllSalesPersons();

        req.setAttribute("salesPersons", salesPersons);
        return "/pages/listSalesPersons.jsp";
    }
}