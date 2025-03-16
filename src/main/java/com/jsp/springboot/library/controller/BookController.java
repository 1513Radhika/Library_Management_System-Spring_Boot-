package com.jsp.springboot.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;
import com.jsp.springboot.library.service.BookService;
import com.jsp.springboot.library.utility.ResponseStructure;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // ✅ Add a new book
    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<Book>> addBook(@RequestBody Book book) {
        System.out.println("Received Book: " + book);
        return bookService.addBook(book);
    }

    // ✅ Get Book by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Optional<Book>>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // ✅ Get Book by ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<ResponseStructure<Optional<Book>>> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    // ✅ Search Books by Title
    @GetMapping("/search/title/{title}")
    public ResponseEntity<ResponseStructure<List<Book>>> searchBooksByTitle(@PathVariable String title) {
        return bookService.searchBooksByTitle(title);
    }

    // ✅ Search Books by Author
    @GetMapping("/search/author/{authorName}")
    public ResponseEntity<ResponseStructure<List<Book>>> searchBooksByAuthor(@PathVariable String authorName) {
        return bookService.searchBooksByAuthor(authorName);
    }

    // ✅ Get Books by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBooksByStatus(@PathVariable BookStatus status) {
        return bookService.getBooksByStatus(status);
    }

    // ✅ Update Book Status
    @PutMapping("/update-status/{bookId}/{status}")
    public ResponseEntity<ResponseStructure<Book>> updateBookStatus(@PathVariable Long bookId, @PathVariable BookStatus status) {
        return bookService.updateBookStatus(bookId, status);
    }
}
