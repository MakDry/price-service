package com.nau.priceservice.module;

import java.util.List;

public interface QueryHandler<T, K> {
    List<T> getAll();
}
