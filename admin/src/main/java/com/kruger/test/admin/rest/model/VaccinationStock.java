package com.kruger.test.admin.rest.model;

import java.time.LocalDate;

public class VaccinationStock implements IModel {

    private Long id;

    private String type;

    private LocalDate date;

    private Short dose;

    private Employee employee;

    public VaccinationStock() {
    }

    public VaccinationStock(Long id, String type, LocalDate date, Short dose, Employee employee) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.dose = dose;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Short getDose() {
        return dose;
    }

    public void setDose(Short dose) {
        this.dose = dose;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String toString() {
        return "VaccinationStockEntity [date=" + date + ", dose=" + dose + ", employee=" + employee + ", id=" + id
                + ", type=" + type + "]";
    }

}
