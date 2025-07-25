package com.warehousebot.warehouse_bot.controller;

import com.warehousebot.warehouse_bot.dao.interfaces.ProductDAO;
import com.warehousebot.warehouse_bot.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDAO dao;

    public ProductController(ProductDAO dao){
        this.dao = dao;
    }

    @PostMapping
    public ResponseEntity<Product> create (@RequestBody Product p){

        Product created = dao.create(p);
        URI location  = URI.create("/products" + created.getId());

        return ResponseEntity
                .created(location)
                .body(created);

    }

    @GetMapping
    public List<Product> list(){
        return dao.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){

        Product p = dao.findById(id);

        return (p != null) ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@PathVariable Long id, @RequestBody Product in){

        in.setId(id);

        boolean ok = dao.update(in);

        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        boolean ok = dao.delete(id);

        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
