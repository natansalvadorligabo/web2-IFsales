package br.com.ifsales.dao.mock;

import br.com.ifsales.model.Product;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductMockDao {
    private final DataSource dataSource;

    public ProductMockDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    public Optional<Product> getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setBrand("MockBrand");
        product.setModel("MockModel");
        product.setModelYear(2023);
        product.setPrice(999.99);
        product.setCategoryId(1L);

        return Optional.of(product);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Product product = new Product();
            product.setId(i);
            product.setBrand("MockBrand" + i);
            product.setModel("MockModel" + i);
            product.setModelYear(2023);
            product.setPrice(999.99 + i);
            product.setCategoryId(1L);

            products.add(product);
        }

        return products;
    }
}