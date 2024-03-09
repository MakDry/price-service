package com.nau.priceservice.util.dto;

import org.springframework.stereotype.Component;

@Component
public class ProductDto {
    private String id;
    private String externalId;
    private String title;

    public ProductDto() { }

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
