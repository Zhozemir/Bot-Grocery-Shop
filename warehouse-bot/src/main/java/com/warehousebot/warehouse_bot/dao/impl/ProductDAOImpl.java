package com.warehousebot.warehouse_bot.dao.impl;

import com.warehousebot.warehouse_bot.dao.interfaces.ProductDAO;
import com.warehousebot.warehouse_bot.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final JdbcTemplate jdbc;

    public ProductDAOImpl(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private final RowMapper<Product> rm = (rs, rowNum) -> new Product (

        rs.getLong("id"),
        rs.getString("name"),
        rs.getBigDecimal("price"),
        rs.getInt("quantity"),
        rs.getInt("location_x"),
        rs.getInt("location_y")
        );

    @Override
    public Product create(Product p){

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO products(name, price, quantity, location_x, location_y) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setInt(4, p.getLocationX());
            ps.setInt(5, p.getLocationY());
            return ps;
        }, kh);
        p.setId(kh.getKey().longValue());
        return p;

    }

    @Override
    public List<Product> list(){
        return jdbc.query("SELECT * FROM products", rm);
    }

    @Override
    public Product findById(Long id){
        return jdbc.queryForObject("SELECT * FROM products WHERE id = ?", rm, id);
    }

    @Override
    public Product findByName(String name){

        List<Product> list = jdbc.query("SELECT * FROM products WHERE LOWER(name) = LOWER(?)", rm, name);

        return list.isEmpty() ? null : list.get(0);

    }

    @Override
    public boolean update(Product p){

        int rows = jdbc.update(
                "UPDATE products SET name = ?, price = ?, quantity = ?, location_x = ?, location_y = ? WHERE id = ?",
                p.getName(), p.getPrice(), p.getQuantity(),
                p.getLocationX(), p.getLocationY(), p.getId()
        );

        return rows > 0;

    }

    @Override
    public boolean delete(Long id){
        return jdbc.update("DELETE FROM products WHERE id = ?", id) > 0;
    }

}
