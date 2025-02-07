package br.com.ifsales.servlets.helpers.funnel;

import br.com.ifsales.dao.FunnelDao;
import br.com.ifsales.model.Funnel;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteFunnelHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long funnelId = Long.parseLong(req.getParameter("id"));
        FunnelDao funnelDao = new FunnelDao(DataSourceSearcher.getInstance().getDataSource());

        return HelperUtils.safeDelete(req, funnelDao.getFunnelById(funnelId), funnelDao, "listFunnels");
    }
}