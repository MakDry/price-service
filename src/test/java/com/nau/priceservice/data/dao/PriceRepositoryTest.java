package com.nau.priceservice.data.dao;

import com.nau.priceservice.MongoDBTestContainerConfig;
import com.nau.priceservice.data.dao.interfaces.PriceRepository;
import com.nau.priceservice.data.entity.PriceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class PriceRepositoryTest {
    private MongoTemplate mongoTemplate;
    private PriceRepository priceDao;
    private PriceEntity priceEntity1;
    private PriceEntity priceEntity2;

    @Autowired
    public PriceRepositoryTest(MongoTemplate mongoTemplate, PriceRepository priceDao) {
        this.mongoTemplate = mongoTemplate;
        this.priceDao = priceDao;
    }

    @BeforeEach
    void setUp() {
        priceEntity1 = new PriceEntity();
        priceEntity1.setId("1");
        priceEntity1.setProductId("101");
        priceEntity1.setCurrency("euro");
        priceEntity1.setUnitAmount(100);
        priceEntity1.setUnitAmountDecimal(100);
        priceEntity1.setPurchasePrice(80);
        priceEntity1.setSuggestedAmount(90);
        priceEntity2 = new PriceEntity();
        priceEntity2.setId("2");
        priceEntity2.setProductId("102");
        priceEntity2.setCurrency("dollar");
        priceEntity2.setUnitAmount(60);
        priceEntity2.setUnitAmountDecimal(60);
        priceEntity2.setPurchasePrice(40);
        priceEntity2.setSuggestedAmount(50);

        mongoTemplate.save(priceEntity1);
        mongoTemplate.save(priceEntity2);
    }

    @AfterEach
    void cleanUp() {
        mongoTemplate.dropCollection(PriceEntity.class);
    }

    @Test
    void PriceRepository_findAll_ReturnsListOfTwoEntities() {
        List<PriceEntity> prices = priceDao.findAll();

        Assertions.assertThat(prices)
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void PriceRepository_findById_ReturnsOptionalEntityWithProperId() {
        Optional<PriceEntity> foundEntity1 = priceDao.findById("1");
        Optional<PriceEntity> foundEntity2 = priceDao.findById("2");

        Assertions.assertThat(foundEntity1).isPresent();
        Assertions.assertThat(foundEntity1.get().getId()).isEqualTo("1");
        Assertions.assertThat(foundEntity2).isPresent();
        Assertions.assertThat(foundEntity2.get().getId()).isEqualTo("2");
    }

    @Test
    void PriceRepository_save_SavesGivenEntityAndReturnsIt() {
        PriceEntity priceEntity3 = new PriceEntity();
        priceEntity3.setId("3");
        priceEntity3.setProductId("103");
        priceEntity3.setCurrency("dollar");
        priceEntity3.setUnitAmount(20);
        priceEntity3.setUnitAmountDecimal(20);
        priceEntity3.setPurchasePrice(10);
        priceEntity3.setSuggestedAmount(15);

        PriceEntity savedEntity = priceDao.save(priceEntity3);
        PriceEntity foundEntity = mongoTemplate.findById("3", PriceEntity.class);

        Assertions.assertThat(savedEntity).isNotNull();
        Assertions.assertThat(savedEntity.getId()).isEqualTo("3");
        Assertions.assertThat(savedEntity.getProductId()).isEqualTo("103");
        Assertions.assertThat(savedEntity.getCurrency()).isEqualTo("dollar");
        Assertions.assertThat(savedEntity.getUnitAmount()).isEqualTo(20);
        Assertions.assertThat(savedEntity.getUnitAmountDecimal()).isEqualTo(20);
        Assertions.assertThat(savedEntity.getPurchasePrice()).isEqualTo(10);
        Assertions.assertThat(savedEntity.getSuggestedAmount()).isEqualTo(15);
        Assertions.assertThat(foundEntity).isNotNull();
        Assertions.assertThat(foundEntity.getId()).isEqualTo("3");
    }

    @Test
    void PriceRepository_update_UpdatesAndReturnsGivenEntity() {
        PriceEntity priceEntity3 = new PriceEntity();
        priceEntity3.setId("2");
        priceEntity3.setProductId("103");
        priceEntity3.setCurrency("euro");
        priceEntity3.setUnitAmount(60);
        priceEntity3.setUnitAmountDecimal(60.5);
        priceEntity3.setPurchasePrice(30);
        priceEntity3.setSuggestedAmount(40.25);

        PriceEntity updatedEntity = priceDao.save(priceEntity3);
        PriceEntity foundEntity = mongoTemplate.findById("2", PriceEntity.class);

        Assertions.assertThat(updatedEntity).isNotNull();
        Assertions.assertThat(updatedEntity.getId()).isEqualTo("2");
        Assertions.assertThat(updatedEntity.getProductId()).isEqualTo("103");
        Assertions.assertThat(updatedEntity.getCurrency()).isEqualTo("euro");
        Assertions.assertThat(updatedEntity.getUnitAmount()).isEqualTo(60);
        Assertions.assertThat(updatedEntity.getUnitAmountDecimal()).isEqualTo(60.5);
        Assertions.assertThat(updatedEntity.getPurchasePrice()).isEqualTo(30);
        Assertions.assertThat(updatedEntity.getSuggestedAmount()).isEqualTo(40.25);
        Assertions.assertThat(foundEntity).isNotNull();
        Assertions.assertThat(foundEntity.getId()).isEqualTo("2");
        Assertions.assertThat(foundEntity.getProductId()).isEqualTo("103");
    }

    @Test
    void PriceRepository_delete_DeletesEntityByObject() {
        priceDao.delete(priceEntity2);

        Assertions.assertThat(mongoTemplate.findAll(PriceEntity.class))
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(mongoTemplate.findById("2", PriceEntity.class)).isNull();
    }

    @Test
    void PriceRepository_findAllByProductId_ReturnLIstOfPricesWithSameProductId() {
        PriceEntity priceEntity3 = new PriceEntity();
        priceEntity3.setId("3");
        priceEntity3.setProductId("102");
        priceEntity3.setCurrency("euro");
        priceEntity3.setUnitAmount(60);
        priceEntity3.setUnitAmountDecimal(60.5);
        priceEntity3.setPurchasePrice(30);
        priceEntity3.setSuggestedAmount(40.25);
        mongoTemplate.save(priceEntity3);

        List<PriceEntity> foundEntities1 = priceDao.findAllByProductId("101");
        List<PriceEntity> foundEntities2 = priceDao.findAllByProductId("102");
        List<PriceEntity> foundEntities3 = priceDao.findAllByProductId("1000");

        Assertions.assertThat(foundEntities1)
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(foundEntities1.get(0).getProductId()).isEqualTo("101");
        Assertions.assertThat(foundEntities2)
                .isNotEmpty()
                .hasSize(2);
        Assertions.assertThat(foundEntities2.get(0).getProductId()).isEqualTo("102");
        Assertions.assertThat(foundEntities2.get(1).getProductId()).isEqualTo("102");
        Assertions.assertThat(foundEntities3).isEmpty();
    }

    @Test
    void PriceRepository_findByIdAndProductId_ReturnsOnePriceEntity() {
        Optional<PriceEntity> foundEntity1 = priceDao.findByIdAndProductId("1", "101");
        Optional<PriceEntity> foundEntity2 = priceDao.findByIdAndProductId("2", "102");

        Assertions.assertThat(foundEntity1).isPresent();
        Assertions.assertThat(foundEntity1.get().getId()).isEqualTo("1");
        Assertions.assertThat(foundEntity1.get().getProductId()).isEqualTo("101");
        Assertions.assertThat(foundEntity2).isPresent();
        Assertions.assertThat(foundEntity2.get().getId()).isEqualTo("2");
        Assertions.assertThat(foundEntity2.get().getProductId()).isEqualTo("102");
    }
}