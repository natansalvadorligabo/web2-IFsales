package br.com.ifsales.servlets.helpers.product;

import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.model.Product;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Optional;

public class SaveProductHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        int modelYear = Integer.parseInt(req.getParameter("modelYear"));
        int totalSales = Integer.parseInt(req.getParameter("totalSales"));
        double price = Double.parseDouble(req.getParameter("price"));
        long categoryId = Long.parseLong(req.getParameter("category"));

        Product product = new Product();
        product.setBrand(brand);
        product.setModel(model);
        product.setModelYear(modelYear);
        product.setPrice(price);
        product.setTotalSales(totalSales);

        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());


        String result = HelperUtils.saveOrUpdate(req, product, productDao, id);
        if (result.equals("registerError")) return "/pages/home/product/productForm.jsp";

        return "redirect?action=listProducts";
    }
}