package com.warehousebot.warehouse_bot.dao.interfaces;

import com.warehousebot.warehouse_bot.model.Order;
import com.warehousebot.warehouse_bot.model.OrderItem;

public interface OrderDAO {

    Order create(Order order);
    boolean updateStatus(Long orderId, String status);
    void addItem(OrderItem item);
    Order findById(Long orderId);

}
