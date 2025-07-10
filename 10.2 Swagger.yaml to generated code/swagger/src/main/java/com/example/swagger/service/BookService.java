package com.example.swagger.service;

import com.example.model.Book;
import com.example.swagger.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.util.ObjectUtils;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public String saveBook(Book book) {
        bookRepository.save(com.example.swagger.entity.Book.mapBookEntity(book));
        return "Book saved with id: "+book.getBookId();
    }



    public Optional<Book> getBook(int bookId) {
        Optional<com.example.swagger.entity.Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapBook(optionalBook.get()));
    }

    private Book mapBook(com.example.swagger.entity.Book bookEntity) {
        Book book = new Book();
        if (!ObjectUtils.isEmpty(bookEntity.getBookId())) {
            book.setBookId(bookEntity.getBookId());
        }
        if (!ObjectUtils.isEmpty(bookEntity.getBookName())) {
            book.setBookName(bookEntity.getBookName());
        }
        if (!ObjectUtils.isEmpty(bookEntity.getPrice())) {
            book.setPrice(bookEntity.getPrice());
        }
        return book;
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
