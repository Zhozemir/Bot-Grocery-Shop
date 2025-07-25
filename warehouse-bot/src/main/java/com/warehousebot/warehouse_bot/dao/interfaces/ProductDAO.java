package com.warehousebot.warehouse_bot.dao.interfaces;

import com.warehousebot.warehouse_bot.model.Product;

import java.util.List;

public interface ProductDAO {

    Product create(Product p);
    List<Product> list();
    Product findById(Long id);
    Product findByName(String name);
    boolean update(Product p);
    boolean delete(Long id);

}
