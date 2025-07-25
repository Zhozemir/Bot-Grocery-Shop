package com.warehousebot.warehouse_bot.dto.route;

import java.util.List;

public class RouteResponse {

    private Long orderId;
    private String status;
    private List<int[]> visitedLocations;

    public RouteResponse(){}

    public RouteResponse(Long orderId, String status, List<int[]> visitedLocations){

        this.orderId = orderId;
        this.status = status;
        this.visitedLocations = visitedLocations;

    }

    public Long getOrderId(){
        return orderId;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<int[]> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(List<int[]> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }
}
