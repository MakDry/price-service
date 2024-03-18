package com.nau.priceservice.messaging.util.mappers.impl;

import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.messaging.util.mappers.interfaces.ProductMessageMapper;
import com.nau.priceservice.messaging.util.messages.ProductMessage;
import org.springframework.stereotype.Component;

@Component("productMessageMapper")
public class ProductMessageMapperImpl implements ProductMessageMapper<ProductMessage, ProductEntity> {
    @Override
    public ProductEntity mapFromMessage(ProductMessage productMessage) {
        if (productMessage == null) {
            return null;
        } else {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setExternalId(productMessage.getId());
            productEntity.setTitle(productMessage.getTitle());
            return productEntity;
        }
    }
}
