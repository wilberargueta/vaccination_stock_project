package com.kruger.test.admin.util.convert;

import com.kruger.test.admin.jpa.entity.UserEntity;
import com.kruger.test.admin.rest.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserConvert extends AbstractConvert<User, UserEntity> {

    private final RoleConvert roleConvert;

    public UserConvert(RoleConvert roleConvert) {
        this.roleConvert = roleConvert;
    }

    @Override
    public User entityToModel(UserEntity entity, Boolean eager) {
        User model = new User();
        model.setId(entity.getId());
        model.setPassword(entity.getPassword());
        model.setUsername(entity.getUsername());

        if (eager && entity.getRol() != null)
            model.setRol(this.roleConvert.entityToModel(entity.getRol()));

        return model;
    }

    @Override
    public UserEntity modelToEntity(User model, Boolean eager) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setPassword(model.getPassword());
        entity.setUsername(model.getUsername());

        if (eager && model.getRol() != null)
            entity.setRol(this.roleConvert.modelToEntity(model.getRol()));

        return entity;
    }

}
