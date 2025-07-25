package com.warehousebot.warehouse_bot.model;

import java.util.List;

public class Order {

    private Long id;
    private String status;
    private List<OrderItem> items;

    public Order(){}

    public Order(Long id, String status, List<OrderItem> items) {
        this.id = id;
        this.status = status;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
