package com.example.swagger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

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
    private int bookId;
    private String bookName;
    private double price;

    public static Book mapBookEntity(com.example.model.Book book) {
        Book bookEntity = new Book();
        if (!ObjectUtils.isEmpty(book.getBookId())) {
            bookEntity.setBookId(book.getBookId());
        }
        if (!ObjectUtils.isEmpty(book.getBookName())) {
            bookEntity.setBookName(book.getBookName());
        }
        if (!ObjectUtils.isEmpty(book.getPrice())) {
            bookEntity.setPrice(book.getPrice());
        }
        return bookEntity;
    }
}
