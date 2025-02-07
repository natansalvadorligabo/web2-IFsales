package br.com.ifsales.servlets.helpers.user;

import br.com.ifsales.servlets.helpers.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp)  {
            HttpSession session = req.getSession();
            session.invalidate();

            return "/pages/login.jsp";
    }
}