package com.kruger.test.admin.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private Long id;

    private String rol;

    private Boolean enable;

    public Role() {
    }

    public Role(Long id, String rol, Boolean enable) {
        this.id = id;
        this.rol = rol;
        this.enable = enable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "RoleEntity [enable=" + enable + ", id=" + id + ", rol=" + rol + "]";
    }

    @Override
    @JsonIgnore
    public String getAuthority() {

        return this.rol;
    }

}
