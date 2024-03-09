package com.nau.priceservice.data.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, K> {
    List<T> findAll();

    Optional<T> save(T t);

    boolean update(T t);

    boolean delete(T t);
}
