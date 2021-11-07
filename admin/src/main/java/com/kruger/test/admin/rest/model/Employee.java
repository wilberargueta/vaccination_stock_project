package com.kruger.test.admin.rest.model;

import java.time.LocalDate;
import java.util.List;

public class Employee implements IModel {

    private Long id;

    private String documentNumber;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthday;

    private String address;

    private String phone;

    private Boolean vaccinated;

    private Boolean delete;

    private User userAccess;

    private List<VaccinationStock> vaccinationStock;

    public Employee() {
    }

    public Employee(Long id, String documentNumber, String firstName, String lastName, String email,
            LocalDate birthday, String address, String phone, Boolean vaccinated, Boolean delete, User userAccess,
            List<VaccinationStock> vaccinationStock) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.vaccinated = vaccinated;
        this.delete = delete;
        this.userAccess = userAccess;
        this.vaccinationStock = vaccinationStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public User getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(User userAccess) {
        this.userAccess = userAccess;
    }

    public List<VaccinationStock> getVaccinationStock() {
        return vaccinationStock;
    }

    public void setVaccinationStock(List<VaccinationStock> vaccinationStock) {
        this.vaccinationStock = vaccinationStock;
    }

    @Override
    public String toString() {
        return "Employee [address=" + address + ", birthday=" + birthday + ", delete=" + delete + ", documentNumber="
                + documentNumber + ", email=" + email + ", firstName=" + firstName + ", id=" + id + ", lastName="
                + lastName + ", phone=" + phone + ", userAccess=" + userAccess + ", vaccinated=" + vaccinated
                + ", vaccinationStock=" + vaccinationStock + "]";
    }

}
