package br.com.ifsales.servlets.helpers.customer;

import br.com.ifsales.dao.CustomerDao;
import br.com.ifsales.model.Customer;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class DeleteCustomerHelper implements Helper {

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long customerId = Long.parseLong(req.getParameter("id"));
        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());

        return HelperUtils.safeDelete(req, customerDao.getCustomerById(customerId), customerDao, "listCustomers");
    }
}