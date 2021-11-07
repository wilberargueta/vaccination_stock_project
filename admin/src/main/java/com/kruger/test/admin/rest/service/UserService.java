package com.kruger.test.admin.rest.service;

import java.util.List;
import java.util.Optional;

import com.kruger.test.admin.jpa.entity.UserEntity;
import com.kruger.test.admin.jpa.repository.UserRepository;
import com.kruger.test.admin.rest.model.User;
import com.kruger.test.admin.util.convert.UserConvert;
import com.kruger.test.admin.util.execption.BadRequestsException;
import com.kruger.test.admin.util.execption.ObjectNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User, UserEntity, UserConvert, UserRepository> {

    private final PasswordEncoder encode;

    public UserService(UserConvert convert, UserRepository repository, PasswordEncoder encode) {
        super(convert, repository);
        this.encode = encode;
    }

    @Override
    public User save(User model) {
        this.LOG.debug("USER => {}", model);
        model.setPassword(encode.encode(model.getPassword()));
        return super.save(model);
    }

    public User getByUsername(String username) {
        if (username == null)
            throw new BadRequestsException("Debe especificar un usuario");

        Optional<UserEntity> optional = this.repository.findByUsernameAndDelete(username, false);
        if (optional.isEmpty())
            throw new ObjectNotFoundException("El usuario no fue encontrado");
        return this.convert.entityToModel(optional.get(), true);

    }

    public void updatePassword(User model) {

        if (model.getId() == null || model.getPassword() == null)
            throw new BadRequestsException("Debe especificar un usuario y una contraseña");

        Optional<UserEntity> entity = this.repository.findByIdAndDelete(model.getId(), false);

        if (entity.isPresent())
            this.repository.changePassword(encode.encode(model.getPassword()), model.getId());
        else
            throw new ObjectNotFoundException("Usuario no encontrado.");

    }

    public boolean usernameValid(String username) {
        if (username == null)
            throw new BadRequestsException("Debe especificar un usuario");

        Optional<UserEntity> optional = this.repository.findByUsername(username);
        return optional.isPresent();
    }

    public void deleteList(List<User> users) {

        users.forEach(user -> this.delete(user.getId()));

    }

}
