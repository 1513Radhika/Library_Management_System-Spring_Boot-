package com.jsp.springboot.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;
import com.jsp.springboot.library.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;


    
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        System.out.println("Received Book: " + book);
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Optional<Book>> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

   
    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Book>> searchBooksByTitle(@PathVariable String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

  
    
    @GetMapping("/search/author/{authorName}")
    public List<Book> searchBooksByAuthor(@PathVariable String authorName) {
        return bookService.searchBooksByAuthor(authorName);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Book>> getBooksByStatus(@PathVariable BookStatus status) {
        return ResponseEntity.ok(bookService.getBooksByStatus(status));
    }

  
    
    @PutMapping("/update-status/{bookId}/{status}")
    public ResponseEntity<String> updateBookStatus(@PathVariable Long bookId, @PathVariable BookStatus status) {
        bookService.updateBookStatus(bookId, status);
        return ResponseEntity.ok("Book status updated successfully.");
    }
    
    
}
