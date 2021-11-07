package com.kruger.test.admin.rest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kruger.test.admin.jpa.entity.AbstractAuditEntity;
import com.kruger.test.admin.jpa.repository.IRepository;
import com.kruger.test.admin.rest.model.IModel;
import com.kruger.test.admin.util.convert.AbstractConvert;
import com.kruger.test.admin.util.execption.BadRequestsException;
import com.kruger.test.admin.util.execption.ObjectNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService<M extends IModel, E extends AbstractAuditEntity, C extends AbstractConvert<M, E>, R extends IRepository<E>> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    protected final C convert;
    protected final R repository;

    public AbstractService(C convert, R repository) {
        this.convert = convert;
        this.repository = repository;
    }

    public M save(M model) {

        this.LOG.debug("SAVE OBJECT => {}", model);

        if (model == null)
            throw new BadRequestsException("Debe enviar un registro en la peticion.");

        E entity = this.convert.modelToEntity(model, true);

        entity.setDelete(false);
        entity.setCreateAt(LocalDateTime.now());
        entity.setCreateBy(1L);

        return this.convert.entityToModel(this.repository.saveAndFlush(entity));

    }

    public M update(M model) {

        if (model == null || model.getId() == null)
            throw new BadRequestsException("Debe enviar un registro en la peticion.");
        Optional<E> optional = this.repository.findByIdAndDelete(model.getId(), false);

        if (optional.isEmpty())
            throw new ObjectNotFoundException("El registro no se encontro en los registros");

        E entity = this.convert.modelToEntity(model, true);
        entity.setDelete(optional.get().getDelete());
        entity.setCreateAt(optional.get().getCreateAt());
        entity.setCreateBy(optional.get().getCreateBy());
        entity.setUpdateAt(LocalDateTime.now());
        entity.setUpdateBy(1L);

        return this.convert.entityToModel(this.repository.save(entity));
    }

    public void delete(Long id) {

        if (id == null)
            throw new BadRequestsException("Debe enviar un id en la consulta de la peticion.");

        Optional<E> optional = this.repository.findByIdAndDelete(id, false);

        if (optional.isEmpty())
            throw new ObjectNotFoundException("El usuario no se encontro en los registros");

        E entity = optional.get();
        entity.setDelete(true);
        entity.setDeleteAt(LocalDateTime.now());
        entity.setDeleteBy(1L);
        this.repository.save(entity);

    }

    public M getById(Long id) {

        if (id == null)
            throw new BadRequestsException("Debe enviar un id en la consulta de la peticion.");

        Optional<E> optional = this.repository.findByIdAndDelete(id, false);

        if (optional.isEmpty())
            throw new ObjectNotFoundException("El rol no se encontro en los registros");

        return this.convert.entityToModel(optional.get(), true);
    }

    public List<M> getAll() {

        return this.repository.findByDelete(false).stream().map(user -> this.convert.entityToModel(user))
                .collect(Collectors.toList());
    }
}
