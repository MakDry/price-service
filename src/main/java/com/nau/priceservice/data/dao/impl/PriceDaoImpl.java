package com.nau.priceservice.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.nau.priceservice.data.dao.interfaces.PriceDao;
import com.nau.priceservice.data.entity.PriceEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("priceDao")
@Transactional
public class PriceDaoImpl implements PriceDao<PriceEntity, String> {

    @Autowired
    private MongoTemplate template;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");
    private static final String ID = "id";
    private static final String CURRENCY = "currency";
    private static final String UNIT_AMOUNT = "unitAmount";
    private static final String UNIT_AMOUNT_DECIMAL = "unitAmountDecimal";
    private static final String PURCHASE_PRICE = "purchasePrice";
    private static final String SUGGESTED_AMOUNT = "suggestedAmount";

    @Override
    public List<PriceEntity> findById(String id) {
        List<PriceEntity> prices = new ArrayList<>();
        prices.add(template.findById(id, PriceEntity.class));
        if (prices.get(0) == null)
            logger.info("In class {} wasn't found any entities with id: {} by findId()",
                    PriceDaoImpl.class.getSimpleName(), id);
        return prices;
    }

    @Override
    public List<PriceEntity> findAll() {
        List<PriceEntity> prices = template.findAll(PriceEntity.class);
        if (prices.isEmpty())
            logger.info("In class {} wasn't found any entities by findAll()", PriceDaoImpl.class.getSimpleName());
        return prices;
    }

    @Override
    public Optional<PriceEntity> save(PriceEntity priceEntity) {
        return Optional.of(template.insert(priceEntity));
    }

    @Override
    public boolean update(PriceEntity priceEntity) {
        Query query = new Query().addCriteria(Criteria.where(ID).is(priceEntity.getId()));
        Update update = new Update();
        update.set(CURRENCY, priceEntity.getCurrency());
        update.set(UNIT_AMOUNT, priceEntity.getUnitAmount());
        update.set(UNIT_AMOUNT_DECIMAL, priceEntity.getUnitAmountDecimal());
        update.set(PURCHASE_PRICE, priceEntity.getPurchasePrice());
        update.set(SUGGESTED_AMOUNT, priceEntity.getSuggestedAmount());
        UpdateResult updateResult = template.updateFirst(query, update, PriceEntity.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean delete(PriceEntity priceEntity) {
        DeleteResult deleteResult = template.remove(priceEntity);
        return deleteResult.getDeletedCount() == 1;
    }
}
