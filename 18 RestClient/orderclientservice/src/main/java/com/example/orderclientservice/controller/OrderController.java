package com.example.orderclientservice.controller;

import com.example.orderclientservice.entity.Product;
import com.example.orderclientservice.restclient.ProductClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ProductClientService productClientService;

    public OrderController(ProductClientService productClientService) {
        this.productClientService = productClientService;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productClientService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestBody Product product) {
        return productClientService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productClientService.deleteProduct(id);
        return "Deleted Successfully";
    }

    // GET
    @GetMapping("/{productId}")
    public String createOrder(@PathVariable Long productId) {

        Product product = productClientService.getProduct(productId);

        return "Order created for product: " + product.name() +
                " Price: " + product.price();
    }
}
