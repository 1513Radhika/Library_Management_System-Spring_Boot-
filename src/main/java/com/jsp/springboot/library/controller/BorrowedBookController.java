package com.jsp.springboot.library.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.service.BookService;
import com.jsp.springboot.library.service.BorrowedBookService;

@RestController
@RequestMapping("/api/borrowed-books")
public class BorrowedBookController {

	@Autowired
	private BorrowedBookService borrowedBookService;
	@Autowired
	private BookService bookService;

	@PostMapping("/borrow")
	public ResponseEntity<?> borrowBook(@RequestBody BorrowRequest borrowRequest) {
		if (borrowRequest.getUserId() == null || borrowRequest.getBookIds() == null
				|| borrowRequest.getBookIds().isEmpty()) {
			return ResponseEntity.badRequest().body("User ID and Book IDs are required");
		}

		List<BorrowedBook> borrowedBooks = new ArrayList<>();
		for (Long bookId : borrowRequest.getBookIds()) {
			BorrowedBook borrowedBook = borrowedBookService.borrowBook(borrowRequest.getUserId(), bookId,
					borrowRequest.getDueDate());
			borrowedBooks.add(borrowedBook);
		}

		return ResponseEntity.ok(borrowedBooks);
	}

	@GetMapping("/get/{id}") // Unique endpoint to avoid confusion
	public ResponseEntity<BorrowedBook> getBorrowedBook(@PathVariable Long id) {
		return ResponseEntity.ok(borrowedBookService.getBorrowedBookById(id).orElseThrow());
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<BorrowedBook>> getUserBorrowedBooks(@PathVariable Long userId) {
		return ResponseEntity.ok(borrowedBookService.getUserBorrowedBooks(userId));
	}

	@GetMapping("/overdue")
	public ResponseEntity<List<BorrowedBook>> getOverdueBooks() {
		List<BorrowedBook> overdueBooks = borrowedBookService.getOverdueBooks();
		return ResponseEntity.ok(overdueBooks);
	}

	@PutMapping("/return/{borrowedBookId}")
	public ResponseEntity<String> returnBook(@PathVariable Long borrowedBookId) {
		borrowedBookService.returnBook(borrowedBookId);
		return ResponseEntity.ok("Book returned successfully.");
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<List<BorrowedBook>> getBorrowedBooksByBook(@PathVariable Long bookId) {
		Optional<Book> book = bookService.getBookById(bookId);
		if (book.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
		List<BorrowedBook> borrowedBooks = borrowedBookService.getBorrowedBooksByBook(book.get());
		if (borrowedBooks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(borrowedBooks);
	}

}
