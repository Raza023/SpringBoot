package com.example.swagger.controller;

import com.example.swagger.entity.Book;
import com.example.swagger.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Tag(name = "Book Controller APIs", description = "Operations related to books")
public class BookController {

    @Autowired
    private BookService service;

    //I can write @RequestMapping as well.
    @PostMapping("/save")
    @Operation(summary = "Create a new book",
            description = "This endpoint allows for creating a new book in the system")
    public String saveBook(@RequestBody @Parameter(description = "Book object to be created",
            required = true) Book book) {
        return service.saveBook(book);
    }

    @Operation(
            summary = "Get a book by ID",
            description = "This endpoint retrieves a specific book by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved book using id."),
                    @ApiResponse(responseCode = "404", description = "Book not found using id.")
            }
    )
    @GetMapping("/get/{bookId}")
    public Book fetchBookById(@PathVariable @Parameter(description = "ID of the book to be retrieved", required = true)
                          int bookId) {
        return service.getBook(bookId).orElseGet(() -> {
            System.out.println("I am getting book with id...." + bookId);
            return null;
        });
        // return service.getBook(bookId).orElse(null);
    }

    @Operation(
            summary = "Get a book by title.",
            description = "This endpoint retrieves a specific book by its title",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved book using title."),
                    @ApiResponse(responseCode = "404", description = "Book not found using title.")
            }
    )
    @GetMapping
    public List<Book> getBooksByTitle(@RequestParam @Parameter(description = "The title of the book to search for",
            required = false) String title) {
        return service.getBooksByName(title);
    }

    @GetMapping("/get")
    @Operation(summary = "Get all books", description = "This endpoint retrieves all books from the system")
    public List<Book> fetchAllBooks() {
        return service.getBooks();
    }

    @DeleteMapping("/delete/{bookId}")
    @Operation(summary = "Delete book by ID", description = "This endpoint delete the book from the system")
    public String deleteBook(@PathVariable @Parameter(description = "ID of the book to be deleted.",
            required = true) int bookId) {
        return service.removeBook(bookId);
    }
}
