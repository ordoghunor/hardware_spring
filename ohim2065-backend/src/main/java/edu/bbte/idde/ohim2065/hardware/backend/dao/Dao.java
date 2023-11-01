package edu.bbte.idde.ohim2065.hardware.backend.dao;

import edu.bbte.idde.ohim2065.hardware.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T create(T entity);

    Collection<T> findAll();

    T findById(Long id);

    Boolean update(T entity);

    void delete(T entity);
}
