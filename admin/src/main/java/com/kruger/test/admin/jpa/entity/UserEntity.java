package com.kruger.test.admin.jpa.entity;

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
@Table(name = "users")
public class UserEntity extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol", referencedColumnName = "id", updatable = true, insertable = true)
    private RoleEntity rol;

    public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, RoleEntity rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEntity getRol() {
        return rol;
    }

    public void setRol(RoleEntity rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", password=" + password + ", rol=" + rol + ", username=" + username + "]";
    }

}
