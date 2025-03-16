package com.jsp.springboot.library.service;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.User;

public interface BorrowedBookService {

    BorrowedBook borrowBook(Long userId, Long bookId, LocalDate dueDate);  // Borrow a book

    Optional<BorrowedBook> getBorrowedBookById(Long id);  // Get borrowed book by ID

    List<BorrowedBook> getBorrowedBooksByUser(User user);  // Get borrowed books by user

    List<BorrowedBook> getBorrowedBooksByBook(Book book);  // Get borrowed books by book

    List<BorrowedBook> getOverdueBooks();  // Get overdue books

    void returnBook(Long borrowedBookId);  // Return a borrowed book

	List<BorrowedBook> getUserBorrowedBooks(Long userId);
}

