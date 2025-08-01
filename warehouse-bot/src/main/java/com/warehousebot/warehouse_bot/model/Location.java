package com.warehousebot.warehouse_bot.model;

public class Location {

    private final int x;
    private final int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }

    public int manhattanDistance(Location other){
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

}
