package br.com.ifsales.servlets.helpers.dashboard;

import br.com.ifsales.servlets.helpers.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DashboardHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        return "/pages/home/dashboard.jsp";
    }
}
