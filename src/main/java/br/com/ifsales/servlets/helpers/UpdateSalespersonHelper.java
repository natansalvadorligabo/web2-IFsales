package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.SalespersonDao;
import br.com.ifsales.model.Salesperson;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateSalespersonHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        SalespersonDao salespersonDao = new SalespersonDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Salesperson> salesperson = salespersonDao.getSalespersonById(Long.parseLong(id));

        if (salesperson.isPresent()) {
            req.setAttribute("salesperson", salesperson.get());
            return "/pages/salespersonForm.jsp";
        }

        return "redirect?action=home";
    }
}