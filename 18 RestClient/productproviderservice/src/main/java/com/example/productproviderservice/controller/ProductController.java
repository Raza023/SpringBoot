package com.example.productproviderservice.controller;

import com.example.productproviderservice.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Map<Long, Product> productDB = new HashMap<>();

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productDB.put(product.id(), product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product) {

        if (!productDB.containsKey(id)) {
            throw new RuntimeException("Product not found");
        }

        productDB.put(id, product);
        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        if (!productDB.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        productDB.remove(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    Product getProductById(@PathVariable Long id) {
        return new Product(id, "Mobile", 150000.00);
    }

}
