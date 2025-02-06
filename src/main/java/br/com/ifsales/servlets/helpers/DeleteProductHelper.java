package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Product;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteProductHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        Long productId = Long.parseLong(req.getParameter("id"));

        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Product> product = productDao.getProductById(productId);


        if (product.isPresent()) {
            if (productDao.delete(product.get())) return "redirect?action=listProducts";
        }

        return "redirect?action=home";
    }
}