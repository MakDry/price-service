package com.nau.priceservice.messaging.service.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.data.dao.interfaces.ProductRepository;
import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.messaging.util.mappers.MessageMapper;
import com.nau.priceservice.messaging.util.messages.ProductMessage;
import com.nau.priceservice.messaging.service.interfaces.ProductMessageService;
import com.nau.priceservice.service.impl.ProductServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productMessageService")
@Transactional
public class ProductMessageServiceImpl implements ProductMessageService<ProductMessage> {

    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");
    private ProductRepository productDao;
    private MessageMapper<ProductMessage, ProductEntity> messageMapper;

    @Autowired
    public ProductMessageServiceImpl(ProductRepository productDao, MessageMapper<ProductMessage,
            ProductEntity> messageMapper) {
        this.productDao = productDao;
        this.messageMapper = messageMapper;
    }

    @Override
    public void create(ProductMessage message) {
        if (message.getId().equals("") || message.getTitle().equals("")) {
            logger.error("In class {} was send message without initialized fields, to save(). Message -> {}",
                    ProductServiceImpl.class.getSimpleName(), message);
            return;
        }
        productDao.save(messageMapper.mapFromMessage(message));
    }

    @Override
    public void update(ProductMessage message) {
        if (message.getId().equals("") || message.getTitle().equals("")) {
            logger.error("In class {} was send message without initialized fields, to update(). Message -> {}",
                    ProductServiceImpl.class.getSimpleName(), message);
            return;
        }

        if (productDao.existsByExternalId(message.getId())) {
            ProductEntity entityToUpdate = productDao.findByExternalId(message.getId());
            entityToUpdate.setTitle(message.getTitle());
            productDao.save(entityToUpdate);
        } else {
            logger.warn("In class {} in method update() wasn't found any entities with id: {}",
                    ProductServiceImpl.class.getSimpleName(), message.getId());
        }
    }

    @Override
    public void delete(ProductMessage message) {
        if (!productDao.existsByExternalId(message.getId())) {
            logger.warn("In class {} method delete() couldn't find any entity with id: {}",
                    ProductServiceImpl.class.getSimpleName(), message.getId());
            return;
        }
        ProductEntity entityToDelete = productDao.findByExternalId(message.getId());
        productDao.delete(entityToDelete);
    }
}