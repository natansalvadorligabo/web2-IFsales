package br.com.ifsales.servlets.helpers.home;

import br.com.ifsales.servlets.helpers.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp)  {
        return "/pages/home/home.jsp";
    }
}
