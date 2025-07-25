package com.warehousebot.warehouse_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class Product {

    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;

    @JsonIgnore
    private int locationX;

    @JsonIgnore
    private int locationY;

    public Product(){}

    public Product(Long id, String name, BigDecimal price, int quantity, int locationX, int locationY) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    @JsonProperty("location")
    public Location getLocation(){
        return new Location(locationX, locationY);
    }

    @JsonProperty("location")
    public void setLocation(Location loc){

        if(loc != null){

            this.locationX = loc.getX();
            this.locationY = loc.getY();

        }

    }

}
