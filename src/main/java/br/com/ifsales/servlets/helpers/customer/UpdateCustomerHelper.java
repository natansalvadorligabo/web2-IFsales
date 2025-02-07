package br.com.ifsales.servlets.helpers.customer;

import br.com.ifsales.dao.CustomerDao;
import br.com.ifsales.model.Customer;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UpdateCustomerHelper implements Helper {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");

        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<Customer> customer = customerDao.getCustomerById(Long.parseLong(id));

        if (customer.isPresent()) {
            req.setAttribute("customer", customer.get());
            return "redirect?action=loadCustomerForm";
        }

        return "redirect?action=listCustomers";
    }
}