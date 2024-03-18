package com.nau.priceservice.messaging.util.messages;

import com.nau.priceservice.messaging.util.messages.enums.ProductEvent;

import java.util.Date;

public class ProductMessage {
    private String id;
    private String title;
    private Date time;
    private ProductEvent event;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ProductEvent getEvent() {
        return event;
    }

    public void setEvent(ProductEvent event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "ProductMessage{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", event=" + event +
                '}';
    }
}
