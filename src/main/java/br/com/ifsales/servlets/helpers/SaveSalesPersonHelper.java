package br.com.ifsales.servlets.helpers;
import br.com.ifsales.dao.SalesPersonDao;
import br.com.ifsales.model.SalesPerson;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class SaveSalesPersonHelper  implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp)  {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        Boolean active = req.getParameter("active") != null;

        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName(name);
        salesPerson.setEmail(email);
        salesPerson.setPhone(phone);
        salesPerson.setActive(active);

        SalesPersonDao salesPersonDao = new SalesPersonDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<SalesPerson> registered = salesPersonDao.getSalesPersonByEmail(email);

        if (registered.isPresent())
        {
            req.setAttribute("result", "already exists");
            return "/pages/salesPersonRegister.jsp";
        }
        else
        {
            if (salesPersonDao.save(salesPerson))
                req.setAttribute("result", "saved");
            else
                req.setAttribute("result", "not saved");

            return "/pages/home.jsp";
        }
    }
}