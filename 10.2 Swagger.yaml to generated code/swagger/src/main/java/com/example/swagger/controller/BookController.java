package com.example.swagger.controller;

import com.example.model.Book;
import com.example.swagger.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    //I can write @RequestMapping as well.
    @PostMapping("/save")
    public String saveBook(@RequestBody Book book) {
        return service.saveBook(book);
    }

    @GetMapping("/get/{bookId}")
    public Book fetchBookById(@PathVariable int bookId) {
        return service.getBook(bookId).orElseGet(() -> {
            System.out.println("I am getting book with id...." + bookId);
            return null;
        });
        // return service.getBook(bookId).orElse(null);
    }


    @GetMapping
    public List<Book> getBooksByTitle(@RequestParam String title) {
        return service.getBooksByName(title);
    }

    @GetMapping("/get")
    public List<Book> fetchAllBooks() {
        return service.getBooks();
    }

    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable int bookId) {
        return service.removeBook(bookId);
    }
}
