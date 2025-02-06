package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.model.Category;
import br.com.ifsales.model.Product;
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
        double price = Double.parseDouble(req.getParameter("price"));
        long categoryId = Long.parseLong(req.getParameter("category"));

        Product product = new Product();
        product.setBrand(brand);
        product.setModel(model);
        product.setModelYear(modelYear);
        product.setPrice(price);

        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());

        if (id == 0) {
            Optional<Product> registered = productDao.getProductById(id);

            if (registered.isPresent()) {
                req.setAttribute("result", "already exists");
                return "/pages/home/productForm.jsp";
            } else {
                if (productDao.save(product)) {
                    req.setAttribute("result", "registered successfully");
                } else {
                    req.setAttribute("result", "not registered");
                }
            }
        } else {
            product.setId(id);

            if (productDao.update(product)) {
                req.setAttribute("result", "saved");
            }
        }

        return "redirect?action=listProducts";
    }
}