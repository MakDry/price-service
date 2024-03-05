package com.nau.priceservice.data.dao.interfaces;

import com.nau.priceservice.data.dao.Dao;
import com.nau.priceservice.data.entity.PriceEntity;

public interface PriceDao<T extends PriceEntity, K extends String> extends Dao<T, K> { }
