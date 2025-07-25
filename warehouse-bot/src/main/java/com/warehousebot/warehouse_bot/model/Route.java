package com.warehousebot.warehouse_bot.model;

public class Route {

    private Long id;
    private Long orderId;
    // индекс в поредицата
    private int seq;
    private int x;
    private int y;

    public Route(){}

    public Route(Long id, Long orderId, int seq, int x, int y) {
        this.id = id;
        this.orderId = orderId;
        this.seq = seq;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
