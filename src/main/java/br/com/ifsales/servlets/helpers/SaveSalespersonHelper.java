package br.com.ifsales.servlets.helpers;
import br.com.ifsales.dao.SalespersonDao;
import br.com.ifsales.model.Salesperson;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class SaveSalespersonHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp)  {
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

        if (id == 0) {
            Optional<Salesperson> registered = salespersonDao.getSalespersonByEmail(email);

            if (registered.isPresent())
            {
                req.setAttribute("result", "already exists");
                return "/pages/salespersonRegister.jsp";
            }
            else
            {
                if (salespersonDao.save(salesperson))
                    req.setAttribute("result", "registered successfully");
                else
                    req.setAttribute("result", "not registered");
            }
        }
        else {
            salesperson.setId(id);

            if (salespersonDao.update(salesperson))
                req.setAttribute("result", "saved");
        }

        return "/pages/home.jsp";
    }
}