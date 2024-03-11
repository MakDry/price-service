package com.nau.priceservice.module.product;

public class ProductCommand {
    private String externalId;
    private String title;

    public ProductCommand(String externalId, String title) {
        this.externalId = externalId;
        this.title = title;
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

    @Override
    public String toString() {
        return "ProductCommand{" +
                "externalId='" + externalId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
