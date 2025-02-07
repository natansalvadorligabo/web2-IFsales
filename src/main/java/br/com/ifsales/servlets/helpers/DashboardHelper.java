package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.FunnelDao;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DashboardHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        FunnelDao funnelDao = new FunnelDao(DataSourceSearcher.getInstance().getDataSource());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

        List<Map<String, Object>> salesByCategory = funnelDao.getSalesByCategory();
        salesByCategory.forEach(item ->
                item.put("formatted_total_sales_category", currencyFormat.format(item.get("total_sales")))
        );

        List<Map<String, Object>> salesByMonth = funnelDao.getSalesByMonth();
        salesByMonth.forEach(item ->
                item.put("formatted_total_sales_month", currencyFormat.format(item.get("total_sales")))
        );

        List<Map<String, Object>> salesByRegion = funnelDao.getSalesByRegion();
        salesByRegion.forEach(item ->
                item.put("formatted_total_sales_region", currencyFormat.format(item.get("total_sales")))
        );

        req.setAttribute("salesByCategory", salesByCategory);
        req.setAttribute("salesByMonth", salesByMonth);
        req.setAttribute("salesByRegion", salesByRegion);
        req.setAttribute("totalSales", funnelDao.getTotalSales().map(currencyFormat::format).orElse("-"));
        req.setAttribute("averageTicket", funnelDao.getAverageTicket().map(currencyFormat::format).orElse("-"));
        req.setAttribute("totalProductsSold", funnelDao.getTotalProductsSold().orElse(0));

        return "/pages/home/dashboard.jsp";
    }
}