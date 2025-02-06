package br.com.ifsales.servlets.helpers.salesperson;

import java.sql.SQLException;
import java.util.Optional;

import br.com.ifsales.dao.SalespersonDao;
import br.com.ifsales.model.Salesperson;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SaveSalespersonHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        Boolean active = req.getParameter("active") != null;

        Salesperson salesperson = new Salesperson();
        salesperson.setName(name);
        salesperson.setEmail(email);
        salesperson.setPhone(phone);
        salesperson.setActive(active);

        SalespersonDao salespersonDao = new SalespersonDao(DataSourceSearcher.getInstance().getDataSource());

        HelperUtils.saveOrUpdate(req, salesperson, salespersonDao, id);

        if (req.getAttribute("result") == "registerError") {
            return "/pages/home/salesperson/salespersonForm.jsp";
        }

        return "redirect?action=listSalespersons";
    }
}