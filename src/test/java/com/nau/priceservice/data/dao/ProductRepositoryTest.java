package com.nau.priceservice.data.dao;

import com.nau.priceservice.MongoDBTestContainerConfig;
import com.nau.priceservice.data.dao.interfaces.ProductRepository;
import com.nau.priceservice.data.entity.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class ProductRepositoryTest {
    private MongoTemplate mongoTemplate;
    private ProductRepository productDao;
    private ProductEntity productEntity1;
    private ProductEntity productEntity2;

    @Autowired
    public ProductRepositoryTest(MongoTemplate mongoTemplate, ProductRepository productDao) {
        this.mongoTemplate = mongoTemplate;
        this.productDao = productDao;
    }

    @BeforeEach
    void setUp() {
        productEntity1 = new ProductEntity();
        productEntity1.setId("1");
        productEntity1.setExternalId("101");
        productEntity1.setTitle("Apples");
        productEntity2 = new ProductEntity();
        productEntity2.setId("2");
        productEntity2.setExternalId("102");
        productEntity2.setTitle("Tomatoes");

        mongoTemplate.save(productEntity1);
        mongoTemplate.save(productEntity2);
    }

    @AfterEach
    void cleanUp() {
        mongoTemplate.dropCollection(ProductEntity.class);
    }

    @Test
    void ProductRepository_findAll_ReturnsListOfTwoEntities() {
        List<ProductEntity> products = productDao.findAll();

        Assertions.assertThat(products)
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void ProductRepository_findById_ReturnsOptionalEntityWithProperId() {
        Optional<ProductEntity> foundEntity1 = productDao.findById("1");
        Optional<ProductEntity> foundEntity2 = productDao.findById("2");

        Assertions.assertThat(foundEntity1).isPresent();
        Assertions.assertThat(foundEntity1.get().getId()).isEqualTo("1");
        Assertions.assertThat(foundEntity2).isPresent();
        Assertions.assertThat(foundEntity2.get().getId()).isEqualTo("2");
    }

    @Test
    void ProductRepository_save_SavesGivenEntityAndReturnsIt() {
        ProductEntity productEntity3 = new ProductEntity();
        productEntity3.setId("3");
        productEntity3.setExternalId("103");
        productEntity3.setTitle("Pineapples");

        ProductEntity savedEntity = productDao.save(productEntity3);
        ProductEntity foundEntity = mongoTemplate.findById("3", ProductEntity.class);

        Assertions.assertThat(savedEntity).isNotNull();
        Assertions.assertThat(savedEntity.getId()).isEqualTo("3");
        Assertions.assertThat(savedEntity.getExternalId()).isEqualTo("103");
        Assertions.assertThat(savedEntity.getTitle()).isEqualTo("Pineapples");
        Assertions.assertThat(foundEntity).isNotNull();
        Assertions.assertThat(foundEntity.getId()).isEqualTo("3");
    }

    @Test
    void ProductRepository_update_UpdatesAndReturnsGivenEntity() {
        ProductEntity productEntity3 = new ProductEntity();
        productEntity3.setId("2");
        productEntity3.setExternalId("103");
        productEntity3.setTitle("Cucumbers");

        ProductEntity updatedEntity = productDao.save(productEntity3);
        ProductEntity foundEntity = mongoTemplate.findById("2", ProductEntity.class);

        Assertions.assertThat(updatedEntity).isNotNull();
        Assertions.assertThat(updatedEntity.getId()).isEqualTo("2");
        Assertions.assertThat(updatedEntity.getExternalId()).isEqualTo("103");
        Assertions.assertThat(updatedEntity.getTitle()).isEqualTo("Cucumbers");
        Assertions.assertThat(foundEntity).isNotNull();
        Assertions.assertThat(foundEntity.getId()).isEqualTo("2");
    }

    @Test
    void ProductRepository_delete_DeletesEntityByObject() {
        productDao.delete(productEntity2);

        Assertions.assertThat(mongoTemplate.findAll(ProductEntity.class))
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(mongoTemplate.findById("2", ProductEntity.class)).isNull();
    }
}