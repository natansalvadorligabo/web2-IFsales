package br.com.ifsales.servlets.helpers.product;

import br.com.ifsales.dao.CategoryDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class LoadProductFormHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        CategoryDao categoryDao = new CategoryDao(DataSourceSearcher.getInstance().getDataSource());
        List<Category> categories = categoryDao.getAllCategories();
        req.setAttribute("categories", categories);

        return "/pages/home/product/productForm.jsp";
    }
}