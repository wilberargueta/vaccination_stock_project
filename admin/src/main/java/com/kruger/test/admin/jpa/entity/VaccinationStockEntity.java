package com.kruger.test.admin.jpa.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "vaccination_stock")
public class VaccinationStockEntity extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "dose", nullable = false)
    private Short dose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "id", updatable = true, insertable = true)
    private EmployeeEntity employee;

    public VaccinationStockEntity() {
    }

    public VaccinationStockEntity(Long id, String type, LocalDate date, Short dose, EmployeeEntity employee) {
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

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "VaccinationStockEntity [date=" + date + ", dose=" + dose + ", employee=" + employee + ", id=" + id
                + ", type=" + type + "]";
    }

}
