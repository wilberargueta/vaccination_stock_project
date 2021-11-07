package com.kruger.test.admin.util.convert;

import com.kruger.test.admin.jpa.entity.RoleEntity;
import com.kruger.test.admin.rest.model.Role;

import org.springframework.stereotype.Component;

@Component
public class RoleConvert extends AbstractConvert<Role, RoleEntity> {

    public Role entityToModel(RoleEntity entity, Boolean eager) {
        Role model = new Role();

        model.setEnable(entity.getEnable());
        model.setId(entity.getId());
        model.setRol(entity.getRol());

        return model;
    }

    public RoleEntity modelToEntity(Role model, Boolean eager) {
        RoleEntity entity = new RoleEntity();

        entity.setEnable(model.getEnable());
        entity.setId(model.getId());
        entity.setRol(model.getRol());

        return entity;
    }
}
