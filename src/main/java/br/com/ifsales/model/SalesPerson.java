package br.com.ifsales.model;

import java.util.Objects;

public class SalesPerson {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesPerson sp = (SalesPerson) o;
        return Objects.equals(id, sp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}