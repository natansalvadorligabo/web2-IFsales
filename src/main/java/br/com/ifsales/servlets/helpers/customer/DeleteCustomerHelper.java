package br.com.ifsales.servlets.helpers.customer;

import br.com.ifsales.dao.CustomerDao;
import br.com.ifsales.model.Customer;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class DeleteCustomerHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long customerId = Long.parseLong(req.getParameter("id"));

        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Customer> customer = customerDao.getCustomerById(customerId);


        if (customer.isPresent()) {
            if (customerDao.delete(customer.get().getId())) return "redirect?action=listCustomers";
        }

        return "redirect?action=home";
    }
}