package com.warehousebot.warehouse_bot.service;

import com.warehousebot.warehouse_bot.dao.interfaces.OrderDAO;
import com.warehousebot.warehouse_bot.dao.interfaces.ProductDAO;
import com.warehousebot.warehouse_bot.dao.interfaces.RouteDAO;
import com.warehousebot.warehouse_bot.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final ProductDAO productDao;
    private final OrderDAO orderDao;
    private final RouteDAO routeDao;

    public OrderService(ProductDAO productDao, OrderDAO orderDao, RouteDAO routeDao){

        this.productDao = productDao;
        this.orderDao = orderDao;
        this.routeDao = routeDao;

    }

    public Order createAndProcessOrder(List<OrderItem> items) {

        Map<OrderItem, Integer> lacking = new LinkedHashMap<>();

        for (OrderItem it : items) {

            Product p = productDao.findById(it.getProductId());
            int have = (p == null ? 0 : p.getQuantity());

            if (have < it.getQuantity())
                lacking.put(it, have);

        }

        Order order = new Order();
        order.setStatus(lacking.isEmpty() ? "SUCCESS" : "FAIL");
        order = orderDao.create(order);

        for(OrderItem it : items){

            it.setOrderId(order.getId());
            orderDao.addItem(it);

        }

        if(!lacking.isEmpty()){
            return order;
        }

        List<Location> pickLocations = new ArrayList<>();

        for(OrderItem it : items){

            Product p = productDao.findById(it.getProductId());
            p.setQuantity(p.getQuantity() - it.getQuantity());
            productDao.update(p);
            pickLocations.add(new Location(p.getLocationX(), p.getLocationY()));

        }

        List<Location> route = RouteCalculator.calculateRoute(pickLocations);
        for(int i = 0; i < route.size(); i++){

            Location loc = route.get(i);
            Route r = new Route();
            r.setOrderId(order.getId());
            r.setSeq(i);
            r.setX(loc.getX());
            r.setY(loc.getY());
            routeDao.create(r);

        }

        return order;

    }

    public Order getOrder(Long orderId){
        return orderDao.findById(orderId);
    }

    public List<Route> getRouteForOrder(Long orderId){
        return routeDao.findByOrderId(orderId);
    }

}
