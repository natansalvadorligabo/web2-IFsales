package br.com.ifsales.model;

import br.com.ifsales.dao.Storable;

import java.time.LocalDate;

public class Customer implements Storable {
    private Long id;
    private String cpf;
    private Region region;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Double income;
    private String mobile;
    private String professionalStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfessionalStatus() {
        return professionalStatus;
    }

    public void setProfessionalStatus(String professionalStatus) {
        this.professionalStatus = professionalStatus;
    }
}