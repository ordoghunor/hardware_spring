package edu.bbte.idde.ohim2065.hardware.spring.dao;

import edu.bbte.idde.ohim2065.hardware.spring.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {

    T saveAndFlush(T entity);

    Collection<T> findAll();

    T getById(Long id);

    void delete(T entity);
}
