package com.nau.priceservice.module;

import java.util.List;

public interface QueryHandler<T, K> {

    List<T> getById(K id);

    List<T> getAll();
}
