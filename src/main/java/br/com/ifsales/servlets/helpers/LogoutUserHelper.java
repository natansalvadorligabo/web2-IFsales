package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.UserDao;
import br.com.ifsales.model.User;
import br.com.ifsales.utils.DataSourceSearcher;
import br.com.ifsales.utils.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LogoutUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp)  {
            HttpSession session = req.getSession();
            session.invalidate();

            return "/pages/login.jsp";
    }
}
