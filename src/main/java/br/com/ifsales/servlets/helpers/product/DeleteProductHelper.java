package br.com.ifsales.servlets.helpers.product;

import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Product;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class DeleteProductHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long productId = Long.parseLong(req.getParameter("id"));
        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());

        return HelperUtils.safeDelete(req, productDao.getProductById(productId), productDao, "listProducts");
    }
}