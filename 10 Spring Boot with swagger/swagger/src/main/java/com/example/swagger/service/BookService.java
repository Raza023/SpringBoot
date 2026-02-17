package com.example.swagger.service;

import com.example.swagger.dao.BookRepository;
import com.example.swagger.entity.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByName(String name) {
        return bookRepository.findByBookName(name);
    }

    public void removeBook(int bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException("Book not found with id: " + bookId);
        }
        bookRepository.deleteById(bookId);
    }

    public Book updateBook(int id, Book book) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        existing.setBookName(book.getBookName());
        existing.setPrice(book.getPrice());
        return bookRepository.save(existing);
    }

    public Book patchBook(int id, Map<String, Object> updates) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        updates.forEach((key, value) -> {
            switch (key) {
                case "bookName":
                    existingBook.setBookName((String) value);
                    break;
                case "price":
                    existingBook.setPrice(Double.parseDouble(value.toString()));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        return bookRepository.save(existingBook);
    }

    public Book patchBookUsingObjectMapper(int id, Map<String, Object> updates) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        try {
            // Convert existing entity to Map
            Map<String, Object> existingMap = objectMapper.convertValue(
                    existingBook,
                    new TypeReference<Map<String, Object>>() {
                    }
            );
            // Merge updates into existing map
            existingMap.putAll(updates);
            // Convert back to entity
            Book updatedBook = objectMapper.convertValue(existingMap, Book.class);
            // Preserve ID (Important)
            updatedBook.setBookId(existingBook.getBookId());
            return bookRepository.save(updatedBook);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid patch data", ex);
        }
    }

    public Book patchBookUsingObjectMapperV2(int id, Map<String, Object> updates) throws JsonMappingException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found with id: " + id));
        //Prevent clients from changing the primary key (ID) during PATCH. Primary Key Should Never Change.
        updates.remove("bookId");
        objectMapper.updateValue(existingBook, updates);
        return existingBook; // JPA auto updates the corresponding values not all as it is patch update.
    }

}
