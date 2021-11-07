package com.kruger.test.admin.util.convert;

public abstract class AbstractConvert<M, E> implements IConvert<M, E> {

    @Override
    public M entityToModel(E entity) {

        return this.entityToModel(entity, false);
    }

    @Override
    public E modelToEntity(M model) {

        return this.modelToEntity(model, false);
    }

}
