package com.nau.priceservice.data.dao.interfaces;

import com.nau.priceservice.data.entity.PriceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("priceDao")
public interface PriceRepository extends MongoRepository<PriceEntity, String> {

    List<PriceEntity> findAllByProductId(String productId);

    Optional<PriceEntity> findByIdAndProductId(String id, String productId);
}
