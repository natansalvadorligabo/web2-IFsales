package br.com.ifsales.servlets.helpers.category;

import br.com.ifsales.dao.CategoryDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Optional;

public class SaveCategoryHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Category category = new Category();
        category.setName(name);
        category.setDescription(description);

        CategoryDao categoryDao = new CategoryDao(DataSourceSearcher.getInstance().getDataSource());

        if (id == 0) {
            Optional<Category> registered = categoryDao.getCategoryByName(name);

            if (registered.isPresent()) {
                req.setAttribute("result", "already exists");
                return "/pages/home/category/customerForm.jsp";
            } else {
                if (categoryDao.save(category))
                    req.setAttribute("result", "registered successfully");
                else
                    req.setAttribute("result", "not registered");
            }
        } else {
            category.setId(id);

            if (categoryDao.update(category))
                req.setAttribute("result", "saved");
        }

        return "redirect?action=listCategories";
    }
}