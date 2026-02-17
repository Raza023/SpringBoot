package com.example.swagger.controller;

import com.example.swagger.entity.Book;
import com.example.swagger.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
//@Tag(name = "Book Controller APIs", description = "Operations related to books") //I am using tags = {"Books"}.
public class BookController {

    private final BookService service;


    //Create Book - POST - /api/books - Add new book
    @Operation(
            summary = "Create a new book",
            description = "This endpoint allows for creating a new book in the system",
            tags = {"Books"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Book object to be created",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Book.class),
                            examples = @ExampleObject(
                                    name = "Sample Book",
                                    value = "{\"bookName\": \"Spring Boot 3\", \"price\": 499.99}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Book created successfully",
                            content = @Content(schema = @Schema(implementation = Book.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    @PostMapping
    public ResponseEntity<Book> saveBook(
            @RequestBody @Parameter(description = "Book object to be created, it is required.",
                    required = true) @Valid Book book) {
        Book saved = service.saveBook(book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @Operation(
            summary = "Get a book by ID",
            description = "Retrieve a specific book by its ID",
            tags = {"Books"},
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Successfully retrieved book using id.",
                            content = @Content(schema = @Schema(implementation = Book.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Book not found using id.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Book> fetchBookById(
            @PathVariable
            @Parameter(
                    description = "ID of the book to be retrieved",
                    required = true
            ) int id) {
        return ResponseEntity.ok(service.getBookById(id));
        // return service.getBook(bookId);
    }

    @Operation(
            summary = "Get books by title.",
            description = "Retrieve books filtered by title. If no title is matched, then return empty list.",
            tags = {"Books"},
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Successfully retrieved book using title.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class)))
                    ),
                    @ApiResponse(responseCode = "404", description = "Book not found using title.")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBooksByTitle(
            @RequestParam
            @Parameter(
                    description = "Optional book title to search for",
                    required = false  //no need to write this line.
            ) String title) {
        List<Book> books = service.getBooksByName(title);
        if (books == null || books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // return ResponseEntity.notFound().build();  //same as above
        }
        return ResponseEntity.ok(books); // 200 OK
    }

    @Operation(
            summary = "Get all books",
            description = "This endpoint retrieves all books from the system",
            tags = {"Books"},
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Successfully retrieved all the books.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class)))
                    ),
                    @ApiResponse(responseCode = "404", description = "Books not found.")
            }
    )
    @GetMapping
    public ResponseEntity<List<Book>> fetchAllBooks() {
        List<Book> books = service.getBooks();
        if (books == null || books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // return ResponseEntity.notFound().build();  //same as above
        }
        return ResponseEntity.ok(books); // 200 OK
    }

    @Operation(
            summary = "Delete book by ID",
            description = "This endpoint deletes the book from the system",
            tags = {"Books"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @DeleteMapping("/{id}") //I can write @RequestMapping("/{id}") as well.
    public ResponseEntity<Void> deleteBook(
            @PathVariable
            @Parameter(
                    description = "ID of the book to be deleted",
                    required = true
            ) int id) {
        service.removeBook(id);
        return ResponseEntity.noContent().build();  // 204
    }

    // UPDATE (Full Update)
    @Operation(
            summary = "Update an existing book",
            description = "Updates the complete details of a book by its ID",
            tags = {"Books"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update book object",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Book.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable
            @Parameter(description = "ID of the book to be updated", required = true) int id,
            @RequestBody @Valid Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
        // return new ResponseEntity<>(service.updateBook(id, book), HttpStatus.OK);  //same as above
        // return ResponseEntity.status(HttpStatus.OK).body(service.updateBook(id, book));  //same as above
    }

    // PARTIAL UPDATE
    @Operation(
            summary = "Partially update a book",
            description = "Updates specific fields of a book using its ID",
            tags = {"Books"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fields to update",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\"price\": 599.99}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchBook(
            @PathVariable
            @Parameter(description = "ID of the book to be updated", required = true) int id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.patchBook(id, updates));
        // return new ResponseEntity<>(service.updateBook(id, book), HttpStatus.OK);  //same as above
        // return ResponseEntity.status(HttpStatus.OK).body(service.updateBook(id, book));  //same as above
    }
}
