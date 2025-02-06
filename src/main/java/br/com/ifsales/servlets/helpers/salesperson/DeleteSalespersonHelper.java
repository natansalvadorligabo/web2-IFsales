package br.com.ifsales.servlets.helpers.salesperson;

import br.com.ifsales.dao.SalespersonDao;
import br.com.ifsales.model.Salesperson;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteSalespersonHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        Long salespersonId = Long.parseLong(req.getParameter("id"));

        SalespersonDao salesPersonDao = new SalespersonDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Salesperson> salesperson = salesPersonDao.getSalespersonById(salespersonId);


        if (salesperson.isPresent()) {
            if (salesPersonDao.delete(salesperson.get())) return "redirect?action=listSalespersons";
        }

        return "redirect?action=home";
    }
}