package br.com.ifsales.servlets.helpers.funnel;

import br.com.ifsales.dao.CustomerDao;
import br.com.ifsales.dao.ProductDao;
import br.com.ifsales.dao.SalespersonDao;
import br.com.ifsales.dao.StoreDao;
import br.com.ifsales.model.Customer;
import br.com.ifsales.model.Product;
import br.com.ifsales.model.Salesperson;
import br.com.ifsales.model.Store;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class LoadFunnelFormHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        CustomerDao regionDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        List<Customer> customers = regionDao.getAllCustomers();
        req.setAttribute("customers", customers);

        SalespersonDao salespersonDao = new SalespersonDao(DataSourceSearcher.getInstance().getDataSource());
        List<Salesperson> salespersons = salespersonDao.getAllSalespersons();
        req.setAttribute("salespersons", salespersons);

        StoreDao storeDao = new StoreDao(DataSourceSearcher.getInstance().getDataSource());
        List<Store> stores = storeDao.getAll();
        req.setAttribute("stores", stores);

        ProductDao productDao = new ProductDao(DataSourceSearcher.getInstance().getDataSource());
        List<Product> products = productDao.getAllProducts();
        req.setAttribute("products", products);


        return "/pages/home/funnel/funnelForm.jsp";
    }
}