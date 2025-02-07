package br.com.ifsales.servlets.helpers.category;

import br.com.ifsales.dao.CategoryDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class DeleteCategoryHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long categoryId = Long.parseLong(req.getParameter("id"));
        CategoryDao categoryDao = new CategoryDao(DataSourceSearcher.getInstance().getDataSource());

        return HelperUtils.safeDelete(req, categoryDao.getCategoryById(categoryId), categoryDao, "listCategories");
    }
}