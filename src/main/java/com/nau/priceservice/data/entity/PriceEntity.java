package com.nau.priceservice.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prices")
public class PriceEntity {
    @Id
    private String id;
    private String productId;
    private String currency;
    private int unitAmount;
    private double unitAmountDecimal;
    private double purchasePrice;
    private double suggestedAmount;

    public PriceEntity() { }

    public PriceEntity(String id, String productId, String currency, int unitAmount,
                       double unitAmountDecimal, double purchasePrice, double suggestedAmount) {
        this.id = id;
        this.productId = productId;
        this.currency = currency;
        this.unitAmount = unitAmount;
        this.unitAmountDecimal = unitAmountDecimal;
        this.purchasePrice = purchasePrice;
        this.suggestedAmount = suggestedAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(int unitAmount) {
        this.unitAmount = unitAmount;
    }

    public double getUnitAmountDecimal() {
        return unitAmountDecimal;
    }

    public void setUnitAmountDecimal(double unitAmountDecimal) {
        this.unitAmountDecimal = unitAmountDecimal;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSuggestedAmount() {
        return suggestedAmount;
    }

    public void setSuggestedAmount(double suggestedAmount) {
        this.suggestedAmount = suggestedAmount;
    }
}
