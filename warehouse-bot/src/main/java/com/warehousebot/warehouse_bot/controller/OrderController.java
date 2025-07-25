package com.warehousebot.warehouse_bot.controller;

import com.warehousebot.warehouse_bot.dao.interfaces.ProductDAO;
import com.warehousebot.warehouse_bot.dto.order.ItemRequest;
import com.warehousebot.warehouse_bot.dto.order.OrderRequest;
import com.warehousebot.warehouse_bot.dto.order.OrderResponse;
import com.warehousebot.warehouse_bot.dto.order.StatusResponse;
import com.warehousebot.warehouse_bot.model.Order;
import com.warehousebot.warehouse_bot.model.OrderItem;
import com.warehousebot.warehouse_bot.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductDAO productDao;

    public OrderController(OrderService orderService, ProductDAO productDao){

        this.orderService = orderService;
        this.productDao = productDao;

    }

    @PostMapping
    public ResponseEntity<OrderResponse> create (@RequestBody OrderRequest req){

        List<OrderItem> items = new ArrayList<>();

        for(ItemRequest ir : req.getItems()){

            var prod = productDao.findByName(ir.getProductName());

            if(prod == null) {
                return ResponseEntity.ok(new OrderResponse("FAIL", "Product not found: " + ir.getProductName()));
            }

            OrderItem it = new OrderItem();
            it.setProductId(prod.getId());
            it.setQuantity(ir.getQuantity());
            items.add(it);

        }

        Order order = orderService.createAndProcessOrder(items);

        if("SUCCESS".equals(order.getStatus())){

            return ResponseEntity.ok(new OrderResponse("SUCCESS", "Your order is ready! Please collect it."));

        } else{

            return ResponseEntity.ok(new OrderResponse("FAIL", "Not enough stock to fulfill your order."));

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusResponse> status (@PathVariable Long id){

        Order order = orderService.getOrder(id);

        if(order == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new StatusResponse(order.getId(), order.getStatus()));

    }

}

