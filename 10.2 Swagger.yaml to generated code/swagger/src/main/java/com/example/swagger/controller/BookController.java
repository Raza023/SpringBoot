package com.example.swagger.controller;

import com.example.model.Book;
import com.example.swagger.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping("/save")
    public String saveBook(@RequestBody Book book) {
        return service.saveBook(book);
    }

    @GetMapping("/{bookId}")
    public Book fetchBookById(@PathVariable int bookId) {
        return service.getBook(bookId).orElseGet(() -> {
            System.out.println("I am getting book with id...." + bookId);
            return null;
        });
    }

    @GetMapping(params = "title")
    public List<Book> getBooksByTitle(@RequestParam("title") String title) {
        return service.getBooksByName(title);
    }

    @GetMapping
    public List<Book> fetchAllBooks() {
        return service.getBooks();
    }

    @PutMapping("/update/{bookId}")
    public String updateBook(@PathVariable int bookId, @RequestBody Book book) {
        book.setBookId(bookId);
        return service.saveBook(book);
    }

    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable int bookId) {
        return service.removeBook(bookId);
    }
}
