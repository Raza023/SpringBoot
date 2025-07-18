package com.example.swagger.dao;

import com.example.swagger.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookNameLike(String name);
}
