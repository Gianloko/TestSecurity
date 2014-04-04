package com.gian.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Francesco Arciello
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {

    PK create(T t);

    T read(Class type, PK id);

    void update(T t);

    void delete(T t);

    List<T> getAll(Class type);
}
