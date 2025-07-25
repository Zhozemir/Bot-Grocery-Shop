package com.warehousebot.warehouse_bot.controller;

import com.warehousebot.warehouse_bot.dto.route.RouteResponse;
import com.warehousebot.warehouse_bot.model.Order;
import com.warehousebot.warehouse_bot.model.Route;
import com.warehousebot.warehouse_bot.service.OrderService;
import com.warehousebot.warehouse_bot.service.RouteCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final OrderService orderService;

    public RouteController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<RouteResponse> getRoute (@RequestParam Long orderId){

        Order order = orderService.getOrder(orderId);

        if(order == null){
            return ResponseEntity.notFound().build();
        }

        List<Route> steps  = orderService.getRouteForOrder(orderId);
        List<int[]> locs = new ArrayList<>();

        for(Route r : steps){
            locs.add(new int[]{r.getX(), r.getY()});
        }

        RouteResponse resp = new RouteResponse(orderId, order.getStatus(), locs);

        return ResponseEntity.ok(resp);

    }


}
