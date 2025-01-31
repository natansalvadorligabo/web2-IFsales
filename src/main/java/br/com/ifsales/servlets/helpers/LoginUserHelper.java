package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.UserDao;
import br.com.ifsales.model.User;
import br.com.ifsales.utils.DataSourceSearcher;
import br.com.ifsales.utils.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<User> user = userDao.getUserByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(PasswordEncoder.encode(password))) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user.get());
            return "redirect?action=home";
        } else {
            req.setAttribute("result", "loginError");
        }

        return "/pages/login.jsp";
    }
}