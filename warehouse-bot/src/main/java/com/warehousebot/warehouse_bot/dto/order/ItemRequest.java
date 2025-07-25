package com.warehousebot.warehouse_bot.dto.order;

public class ItemRequest {

    private String productName;
    private int quantity;

    public ItemRequest(){}

    public ItemRequest(String productName, int quantity){

        this.productName = productName;
        this.quantity = quantity;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

}
