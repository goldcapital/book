package com.example.library_project.controller;

import com.example.library_project.dto.BookDTO;
import com.example.library_project.dto.request.BookRequest;
import com.example.library_project.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    @Operation(summary = "API for creating a new book",
            description = "This API is used for creating a new book entry.")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }
    @GetMapping("/book_all")
    public ResponseEntity<List<BookDTO>>getAllBooks(){
        return ResponseEntity .ok(bookService.getAllBooks());
    }

    @GetMapping("/pagination")
    @Operation(summary = "API for retrieving paginated book list",
            description = "This API is used for getting a paginated list of books.")
    public ResponseEntity<PageImpl<BookDTO>> pagination(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(bookService.pagination(page, size));
    }

    @GetMapping("/get_by_id/{id}")
    @Operation(summary = "API for retrieving a book by ID",
            description = "This API is used for getting a book entry by its ID.")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "API for updating a book",
            description = "This API is used for updating an existing book entry by its ID.")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookRequest bookDetails) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }
    }

