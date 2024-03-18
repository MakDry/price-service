package com.nau.priceservice.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class ProductEntity {
    @Id
    private String id;
    private String externalId;
    private String title;

    public ProductEntity() { }

    public ProductEntity(String id, String externalId, String title) {
        this.id = id;
        this.externalId = externalId;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
