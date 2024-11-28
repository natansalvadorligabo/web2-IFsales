package br.com.ifsales.model;

public class Store {
    private Long id;
    private String name;
    private String cnpj;
    private String region;
    private String address;
    private String phone;

    public Store() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnjp() {
        return cnpj;
    }

    public void setCnjp(String cnjp) {
        this.cnpj = cnjp;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
