package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.SalesPersonDao;
import br.com.ifsales.model.SalesPerson;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateSalesPersonHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = req.getParameter("email");

        SalesPersonDao salesPersonDao = new SalesPersonDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<SalesPerson> salesPerson = salesPersonDao.getSalesPersonByEmail(email);

        if(salesPerson.isPresent())
        {
            req.setAttribute("salesPerson", salesPerson.get());
            return "/pages/salesPersonRegister.jsp";
        }

        return "redirect?action=home";
    }
}