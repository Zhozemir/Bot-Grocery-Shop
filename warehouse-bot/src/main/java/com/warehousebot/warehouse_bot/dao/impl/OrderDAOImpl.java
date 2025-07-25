package com.warehousebot.warehouse_bot.dao.impl;

import com.warehousebot.warehouse_bot.dao.interfaces.OrderDAO;
import com.warehousebot.warehouse_bot.model.Order;
import com.warehousebot.warehouse_bot.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final JdbcTemplate jdbc;

    public OrderDAOImpl(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private final RowMapper<Order> orderMapper = (rs, rowNum) -> {

        Order o = new Order();
        o.setId(rs.getLong("id"));
        o.setStatus(rs.getString("status"));
        return o;

    };

    private final RowMapper<OrderItem> itemMapper = (rs, rowNum) -> {

        OrderItem it = new OrderItem();
        it.setId(rs.getLong("id"));
        it.setOrderId(rs.getLong("order_id"));
        it.setProductId(rs.getLong("product_id"));
        it.setQuantity(rs.getInt("quantity"));
        return it;

    };

    @Override
    public Order create(Order order){

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO orders(status) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, order.getStatus());
            return ps;

        }, kh);

        order.setId(kh.getKey().longValue());
        return order;

    }

    @Override
    public boolean updateStatus(Long orderId, String status){

        int updated = jdbc.update(
                "UPDATE orders SET status = ? WHERE id = ?",
                status, orderId
        );

        return updated > 0;

    }

    @Override
    public void addItem(OrderItem item){

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO order_items(order_id, product_id, quantity) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, item.getOrderId());
            ps.setLong(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            return ps;
        }, kh);

        item.setId(kh.getKey().longValue());

    }

    @Override
    public Order findById(Long orderId){

        Order order = jdbc.queryForObject("SELECT * FROM orders WHERE id = ?", orderMapper, orderId);

        List<OrderItem> items = jdbc.query("SELECT * FROM order_items WHERE order_id = ?", itemMapper, orderId);

        order.setItems(items);
        return order;

    }

}
