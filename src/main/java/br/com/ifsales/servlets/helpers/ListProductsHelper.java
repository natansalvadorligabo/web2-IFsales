package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.CategoriesDao;
import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.model.Product;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListProductsHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());
        List<Product> products = productDao.getAllProducts()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        req.setAttribute("products", products);

        return "/pages/home/productTable.jsp";
    }
}