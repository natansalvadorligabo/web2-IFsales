package br.com.ifsales.servlets.helpers.customer;

import br.com.ifsales.dao.CustomerDao;
import br.com.ifsales.model.Customer;
import br.com.ifsales.model.Region;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.time.LocalDate;

public class SaveCustomerHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        String cpf = req.getParameter("cpf");
        long regionId =  Long.parseLong(req.getParameter("region"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));
        Double income = Double.parseDouble(req.getParameter("income"));
        String mobile = req.getParameter("mobile");
        String professionalStatus = req.getParameter("professionalStatus");

        Customer customer = new Customer();
        customer.setCpf(cpf);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setBirthDate(birthDate);
        customer.setIncome(income);
        customer.setMobile(mobile);
        customer.setProfessionalStatus(professionalStatus);

        Region region = new Region();
        region.setId(regionId);
        customer.setRegion(region);

        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());


        String result = HelperUtils.saveOrUpdate(req, customer, customerDao, id);
        if (result.equals("registerError")) return "redirect?action=loadCustomerForm";

        return "redirect?action=listCustomers";
    }
}