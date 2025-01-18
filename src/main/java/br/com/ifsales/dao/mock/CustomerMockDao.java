package br.com.ifsales.dao.mock;

import br.com.ifsales.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerMockDao {
    public Optional<Customer> getCustomerById(Long customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setCpf("123.456.789-00");
        customer.setRegionId(1L);
        customer.setFirstName("MockFirstName");
        customer.setLastName("MockLastName");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));
        customer.setIncome(5000.0);
        customer.setMobile("123456789012345");
        customer.setProfessionalStatus("Employed");

        return Optional.of(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Customer customer = new Customer();
            customer.setId(i);
            customer.setCpf("123.456.789-" + String.format("%02d", i));
            customer.setRegionId(i);
            customer.setFirstName("MockFirstName" + i);
            customer.setLastName("MockLastName" + i);
            customer.setBirthDate(LocalDate.of(1990, 1, (int) i));
            customer.setIncome(5000.0 + (i * 100));
            customer.setMobile("12345678901234" + i);
            customer.setProfessionalStatus("Status" + i);

            customers.add(customer);
        }

        return customers;
    }
}