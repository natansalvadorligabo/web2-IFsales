package br.com.ifsales.servlets.helpers.funnel;

import br.com.ifsales.dao.FunnelDao;
import br.com.ifsales.model.Funnel;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateFunnelHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        FunnelDao funnelDao = new FunnelDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Funnel> funnel = funnelDao.getFunnelById(Long.parseLong(id));

        if (funnel.isPresent()) {
            req.setAttribute("funnel", funnel.get());
            return "redirect?action=loadFunnelForm";
        }

        return "redirect?action=listFunnels";
    }
}