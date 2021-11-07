package com.kruger.test.admin.util.convert;

public interface IConvert<M, E> {

    public M entityToModel(E entity);

    public E modelToEntity(M model);

    public M entityToModel(E entity, Boolean eager);

    public E modelToEntity(M model, Boolean eager);
}
