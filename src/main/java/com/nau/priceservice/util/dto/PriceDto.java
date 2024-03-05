package com.nau.priceservice.util.dto;

import org.springframework.stereotype.Component;

@Component
public class PriceDto {
    private String id;
    private String productId;
    private String currency;
    private int unitAmount;
    private double unitAmountDecimal;
    private double purchasePrice;
    private double suggestedAmount;

    public PriceDto() { }

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
