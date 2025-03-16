package com.jsp.springboot.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.utility.ResponseStructure;

import java.time.LocalDate;

public interface BorrowedBookService {
    ResponseEntity<ResponseStructure<Optional<BorrowedBook>>> getBorrowedBookById(Long id);

    ResponseEntity<ResponseStructure<List<BorrowedBook>>> getBorrowedBooksByUser(Long userId);

    ResponseEntity<ResponseStructure<List<BorrowedBook>>> getBorrowedBooksByBook(Long bookId);

    ResponseEntity<ResponseStructure<List<BorrowedBook>>> getOverdueBooks();

    ResponseEntity<ResponseStructure<String>> returnBook(Long borrowedBookId);

    ResponseEntity<ResponseStructure<List<BorrowedBook>>> getUserBorrowedBooks(Long userId);

    ResponseEntity<ResponseStructure<List<BorrowedBook>>> borrowBooks(Long userId, List<Long> bookIds, LocalDate dueDate);
}
