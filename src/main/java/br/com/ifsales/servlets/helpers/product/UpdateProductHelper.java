package br.com.ifsales.servlets.helpers.product;

import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Product;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateProductHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Product> product = productDao.getProductById(Long.parseLong(id));

        if (product.isPresent()) {
            req.setAttribute("product", product.get());
            return "redirect?action=loadProductForm";
        }

        return "redirect?action=listProducts";
    }
}