package com.example.swagger.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "Books")
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

    //@NotBlank is used by @Valid or @Validated for request validations of POST / PUT endpoints in controller:
    @NotBlank(message = "Book name is required")
    @Schema(description = "Name of the book", example = "Spring Boot in Action", required = true)
    private String bookName;

    //@Positive is used by @Valid or @Validated for request validations of POST / PUT endpoints in controller:
    @Positive(message = "Price must be positive")
    @Schema(description = "Price of the book", example = "29.99", required = true)
    private double price;
}
