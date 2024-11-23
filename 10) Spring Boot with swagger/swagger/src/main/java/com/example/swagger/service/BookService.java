package com.example.swagger.service;

import com.example.swagger.dao.BookRepository;
import com.example.swagger.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public String saveBook(Book book) {
        bookRepository.save(book);
        return "Book saved with id: "+book.getBookId();
    }

    public Optional<Book> getBook(int bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByName(String name) {
        return bookRepository.findByBookName(name);
    }

    public String removeBook(int bookId) {
        bookRepository.deleteById(bookId);
        return "Book deleted with id: "+bookId;
    }
}
