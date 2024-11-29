package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.SalesPersonDao;
import br.com.ifsales.model.SalesPerson;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteSalesPersonHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        Long salesPersonId = Long.parseLong(req.getParameter("id"));

        SalesPersonDao salesPersonDao = new SalesPersonDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<SalesPerson> salesPerson = salesPersonDao.getSalesPersonById(salesPersonId);

        if(salesPerson.isEmpty())
            return "/";

        if (!salesPersonDao.delete(salesPerson.get()))
            return "/";

        return "/ControllerServlet?action=listSalesPersons";
    }
}