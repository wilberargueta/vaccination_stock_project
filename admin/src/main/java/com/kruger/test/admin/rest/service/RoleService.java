package com.kruger.test.admin.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kruger.test.admin.jpa.entity.RoleEntity;
import com.kruger.test.admin.jpa.repository.RoleRepository;
import com.kruger.test.admin.rest.model.Role;
import com.kruger.test.admin.util.convert.RoleConvert;
import com.kruger.test.admin.util.execption.BadRequestsException;
import com.kruger.test.admin.util.execption.ObjectNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final RoleConvert roleConvert;
    private final RoleRepository roleRepository;

    public RoleService(RoleConvert roleConvert, RoleRepository roleRepository) {
        this.roleConvert = roleConvert;
        this.roleRepository = roleRepository;
    }

    public Role save(Role model) {

        if (model == null)
            throw new BadRequestsException("Debe enviar un rol en la peticion.");

        RoleEntity entity = this.roleConvert.modelToEntity(model);

        return this.roleConvert.entityToModel(this.roleRepository.save(entity));
    }

    public Role update(Role model) {

        if (model == null || model.getId() == null)
            throw new BadRequestsException("Debe enviar un rol en la peticion.");
        Optional<RoleEntity> optional = this.roleRepository.findById(model.getId());

        if (optional.isEmpty())
            throw new ObjectNotFoundException("El rol no se encontro en los registros");

        RoleEntity entity = this.roleConvert.modelToEntity(model);

        return this.roleConvert.entityToModel(this.roleRepository.save(entity));
    }

    public void delete(Long id) {

        if (id == null)
            throw new BadRequestsException("Debe enviar un id en la consulta de la peticion.");

        Optional<RoleEntity> optional = this.roleRepository.findByIdAndEnable(id, true);

        if (optional.isEmpty())
            throw new ObjectNotFoundException("El rol no se encontro en los registros");

        Role model = this.roleConvert.entityToModel(optional.get());
        model.setEnable(false);
        this.update(model);
    }

    public Role getById(Long id) {

        if (id == null)
            throw new BadRequestsException("Debe enviar un id en la consulta de la peticion.");

        Optional<RoleEntity> optional = this.roleRepository.findByIdAndEnable(id, true);

        if (optional.isEmpty())
            throw new ObjectNotFoundException("El rol no se encontro en los registros");

        return this.roleConvert.entityToModel(optional.get());
    }

    public List<Role> getAll() {

        return this.roleRepository.findByEnable(true).stream().map(rol -> this.roleConvert.entityToModel(rol))
                .collect(Collectors.toList());
    }

}
