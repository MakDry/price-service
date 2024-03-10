package com.nau.priceservice.data.dao.interfaces;

import com.nau.priceservice.data.dao.Dao;
import com.nau.priceservice.data.entity.ProductEntity;

public interface ProductDao<T extends ProductEntity, K extends String> extends Dao<T, K> {
    boolean isExists(K k);
}
