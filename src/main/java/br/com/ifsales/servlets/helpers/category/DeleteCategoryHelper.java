package br.com.ifsales.servlets.helpers.category;

import br.com.ifsales.dao.CategoryDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteCategoryHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long categoryId = Long.parseLong(req.getParameter("id"));

        CategoryDao categoryDao = new CategoryDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Category> category = categoryDao.getCategoryById(categoryId);

        if (category.isPresent())
            if (categoryDao.delete(category.get().getId())) return "redirect?action=listCategories";

        return "redirect?action=home";
    }
}