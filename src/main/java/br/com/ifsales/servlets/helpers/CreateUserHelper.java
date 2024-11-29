package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.UserDao;
import br.com.ifsales.model.User;
import br.com.ifsales.utils.DataSourceSearcher;
import br.com.ifsales.utils.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp)  {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordEncoder.encode(password));

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        if (userDao.save(user)) {
            req.setAttribute("result", "registered");
            return "/pages/login.jsp";
        } else {
            req.setAttribute("result", "notRegistered");
            return "/pages/user-register.jsp";
        }
    }
}
