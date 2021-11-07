package com.kruger.test.admin.jpa.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class EmployeeEntity extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_number", unique = true, nullable = false)
    private String documentNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "vaccinated", nullable = false)
    private Boolean vaccinated;

    @Column(name = "delete", nullable = false)
    private Boolean delete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_access", referencedColumnName = "id", updatable = true, insertable = true)
    private UserEntity userAccess;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VaccinationStockEntity> vaccinationStock;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Long id, String documentNumber, String firstName, String lastName, String email,
            LocalDate birthday, String address, String phone, Boolean vaccinated, Boolean delete, UserEntity userAccess,
            List<VaccinationStockEntity> vaccinationStock) {
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

    public UserEntity getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(UserEntity userAccess) {
        this.userAccess = userAccess;
    }

    public List<VaccinationStockEntity> getVaccinationStock() {
        return vaccinationStock;
    }

    public void setVaccinationStock(List<VaccinationStockEntity> vaccinationStock) {
        this.vaccinationStock = vaccinationStock;
    }

    @Override
    public String toString() {
        return "EmployeeEntity [address=" + address + ", birthday=" + birthday + ", delete=" + delete
                + ", documentNumber=" + documentNumber + ", email=" + email + ", firstName=" + firstName + ", id=" + id
                + ", lastName=" + lastName + ", phone=" + phone + ", userAccess=" + userAccess + ", vaccinated="
                + vaccinated + ", vaccinationStock=" + vaccinationStock + "]";
    }

}
