package br.com.ifsales.servlets.helpers.category;

import br.com.ifsales.dao.CategoryDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateCategoryHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        CategoryDao categoryDao = new CategoryDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Category> category = categoryDao.getCategoryById(Long.parseLong(id));

        if (category.isPresent()) {
            req.setAttribute("category", category.get());
            return "/pages/home/category/categoryForm.jsp";
        }

        return "redirect?action=listCategories";
    }
}