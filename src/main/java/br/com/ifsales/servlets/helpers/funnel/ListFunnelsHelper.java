package br.com.ifsales.servlets.helpers.funnel;

import br.com.ifsales.dao.FunnelDao;
import br.com.ifsales.model.Funnel;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class ListFunnelsHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        FunnelDao funnelDao = new FunnelDao(DataSourceSearcher.getInstance().getDataSource());
        List<Funnel> funnels = funnelDao.getAllFunnels();
        req.setAttribute("funnels", funnels);

        return "/pages/home/funnel/funnelTable.jsp";
    }
}