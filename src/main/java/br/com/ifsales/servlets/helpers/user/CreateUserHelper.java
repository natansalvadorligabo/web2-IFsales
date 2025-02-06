package br.com.ifsales.servlets.helpers.user;

import br.com.ifsales.dao.UserDao;
import br.com.ifsales.model.User;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import br.com.ifsales.utils.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class CreateUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordEncoder.encode(password));

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        HelperUtils.saveOrUpdate(req, user, userDao, 0);

        if (req.getAttribute("result") != "registerError") {
            return "redirect?action=login";
        }

        return "/pages/userRegister.jsp";
    }
}
