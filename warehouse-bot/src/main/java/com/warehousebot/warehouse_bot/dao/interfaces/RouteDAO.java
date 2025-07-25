package com.warehousebot.warehouse_bot.dao.interfaces;

import com.warehousebot.warehouse_bot.model.Route;

import java.util.List;

public interface RouteDAO {

    void create(Route route);
    List<Route> findByOrderId(Long orderId);

}
