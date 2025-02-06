package br.com.ifsales.model;

import br.com.ifsales.dao.Storable;

import java.time.LocalDate;

public class Funnel implements Storable {
    private Long id;
    private Customer customer;
    private Salesperson salesperson;
    private Store store;
    private Product product;
    private LocalDate paidDate;
    private Double discount;
    private int productQuanity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Salesperson getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public int getProductQuanity() {
        return productQuanity;
    }

    public void setProductQuanity(int productQuanity) {
        this.productQuanity = productQuanity;
    }
}