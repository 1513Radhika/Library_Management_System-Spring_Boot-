package com.jsp.springboot.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.exception.BookUnavailableException;
import com.jsp.springboot.library.repository.BookRepository;
import com.jsp.springboot.library.service.BorrowedBookService;
import com.jsp.springboot.library.utility.ResponseStructure;

@RestController
@RequestMapping("/api/borrowed-books")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService borrowedBookService;

    // ✅ Borrow multiple books
    @PostMapping("/borrow")
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> borrowBooks(@RequestBody BorrowRequest borrowRequest) {
        return borrowedBookService.borrowBooks(borrowRequest.getUserId(), borrowRequest.getBookIds(), borrowRequest.getDueDate());
    }

    // ✅ Get Borrowed Book by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseStructure<Optional<BorrowedBook>>> getBorrowedBook(@PathVariable Long id) {
        return borrowedBookService.getBorrowedBookById(id);
    }

    // ✅ Get all borrowed books for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getUserBorrowedBooks(@PathVariable Long userId) {
        return borrowedBookService.getUserBorrowedBooks(userId);
    }

    // ✅ Get all overdue books
    @GetMapping("/overdue")
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getOverdueBooks() {
        return borrowedBookService.getOverdueBooks();
    }

    // ✅ Return a borrowed book
    @PutMapping("/return/{borrowedBookId}")
    public ResponseEntity<ResponseStructure<String>> returnBook(@PathVariable Long borrowedBookId) {
        return borrowedBookService.returnBook(borrowedBookId);
    }

    // ✅ Get borrowed books by book ID
    @GetMapping("/book/{bookId}")
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getBorrowedBooksByBook(@PathVariable Long bookId) {
        return borrowedBookService.getBorrowedBooksByBook(bookId);
    }
    
 // ✅ Get all borrowed books for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getBorrowedBooksByUser(@PathVariable Long userId) {
        return borrowedBookService.getBorrowedBooksByUser(userId);
    }

}
