package com.example.swagger.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book implements Serializable {

    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier for the book", example = "1", required = true)
    private int bookId;
    @Schema(description = "Name of the book", example = "Spring Boot in Action", required = true)
    private String bookName;
    @Schema(description = "Price of the book", example = "29.99", required = true)
    private double price;
}
