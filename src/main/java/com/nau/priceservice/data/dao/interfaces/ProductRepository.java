package com.nau.priceservice.data.dao.interfaces;

import com.nau.priceservice.data.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public interface ProductRepository extends MongoRepository<ProductEntity, String> { }
