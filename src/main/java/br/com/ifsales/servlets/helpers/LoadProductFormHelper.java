package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.CategoriesDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.model.Product;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LoadProductFormHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        CategoriesDao categoryDao = new CategoriesDao(DataSourceSearcher.getInstance().getDataSource());
        List<Category> categories = categoryDao.getAllCategories()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        req.setAttribute("categories", categories);

        return "/pages/home/productForm.jsp";
    }
}