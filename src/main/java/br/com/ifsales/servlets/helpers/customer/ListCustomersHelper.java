package br.com.ifsales.servlets.helpers.customer;

import br.com.ifsales.dao.CustomerDao;
import br.com.ifsales.model.Customer;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class ListCustomersHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        List<Customer> customers = customerDao.getAllCustomers();
        req.setAttribute("customers", customers);

        return "/pages/home/customer/customerTable.jsp";
    }
}