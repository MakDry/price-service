package com.nau.priceservice.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.nau.priceservice.data.dao.interfaces.ProductDao;
import com.nau.priceservice.data.entity.ProductEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("productDao")
@Transactional
public class ProductDaoImpl implements ProductDao<ProductEntity, String> {

    @Autowired
    private MongoTemplate template;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");
    private static final String ID = "id";
    private static final String TITLE = "location";

    @Override
    public List<ProductEntity> findAll() {
        List<ProductEntity> products = template.findAll(ProductEntity.class);
        if (products.isEmpty())
            logger.info("In class {} wasn't found any entities by findAll()", ProductDaoImpl.class.getSimpleName());
        return products;
    }

    @Override
    public Optional<ProductEntity> save(ProductEntity productEntity) {
        return Optional.of(template.insert(productEntity));
    }

    @Override
    public boolean update(ProductEntity productEntity) {
        Query query = new Query().addCriteria(Criteria.where(ID).is(productEntity.getId()));
        Update update = new Update();
        update.set(TITLE, productEntity.getTitle());
        UpdateResult updateResult = template.updateFirst(query, update, ProductEntity.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean delete(ProductEntity productEntity) {
        DeleteResult deleteResult = template.remove(productEntity);
        return deleteResult.getDeletedCount() == 1;
    }

    @Override
    public boolean isExists(String id) {
        ProductEntity product = template.findById(id, ProductEntity.class);
        if (product != null) {
            return true;
        } else {
            logger.info("In class {} wasn't found any entities with id: {} by isExists()",
                    ProductDaoImpl.class.getSimpleName(), id);
            return false;
        }
    }
}
