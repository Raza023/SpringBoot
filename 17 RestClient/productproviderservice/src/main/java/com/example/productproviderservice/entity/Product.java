package com.example.productproviderservice.entity;

public record Product(
        Long id,
        String name,
        double price
) {
}
