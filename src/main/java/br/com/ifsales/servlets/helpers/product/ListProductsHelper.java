package br.com.ifsales.servlets.helpers.product;

import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Product;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class ListProductsHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());
        List<Product> products = productDao.getAllProducts();
        req.setAttribute("products", products);

        return "/pages/home/product/productTable.jsp";
    }
}