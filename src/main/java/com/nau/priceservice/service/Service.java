package com.nau.priceservice.service;

import java.util.List;

public interface Service<T> {

    List<T> getById(String id);

    List<T> getAll();
}
